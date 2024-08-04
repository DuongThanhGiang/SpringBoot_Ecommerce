package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.*;
import com.springboot.bootstrap.entity.DTO.SanPhamQrDTO;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.HoaDonTLRepo;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.repository.PhieuGiamGiaRepository;
import com.springboot.bootstrap.service.DanhMucService;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import com.springboot.bootstrap.service.KichThuocService;
import com.springboot.bootstrap.service.MauSacService;
import com.springboot.bootstrap.service.NhanVienService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.service.ThuongHieuService;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/giao_dich")
public class ThanhToanController {
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private DanhMucService danhMucService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private HoaDonTLRepo hoaDonTLRepo;
    @Autowired
    private AnhRepo anhRepo;
    @Autowired
    private Base64Image base64Image;
    @Autowired
    private NhanVienService nhanVienService;
    @GetMapping("")
    public String getAll(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        List<DanhMuc> listDM = danhMucService.findAllByTrangThai();
        List<ThuongHieu> listTH = thuongHieuService.findAllByTrangThai();
        List<KichThuoc> listKT = kichThuocService.findAllByTrangThai();
        List<MauSac> listMS = mauSacService.findAllByTrangThai();
        List<KhachHang> khachHang = khachHangService.findAll();
        List<HoaDon> listHD = hoaDonService.renderTab();
        Map<String, Anh> mapAnhSanPham = new HashMap<>();
        for (HoaDon hd : listHD) {
            for (HoaDonChiTiet hdct:hd.getListhdct()) {
                List<Anh> listAnh = anhRepo.findAllBySanPham(hdct.getSanPhamChiTiet().getSanPham());
                Anh anh = listAnh.get(0);
                mapAnhSanPham.put(hdct.getSanPhamChiTiet().getSanPham().getId(), anh);
            }
        }
        model.addAttribute("listKH", khachHang);
        model.addAttribute("listHD", listHD);
        model.addAttribute("listTH", listTH);
        model.addAttribute("listDM", listDM);
        model.addAttribute("listKT", listKT);
        model.addAttribute("listMS", listMS);
        model.addAttribute("mapAnhSanPham", mapAnhSanPham);
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("listMS", listMS);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("namenv",nhanVien.getTen());
        model.addAttribute("spqr",new SanPhamQrDTO());
        return "/pages/giao_dich";
    }

    @PostMapping("/add_tab")
    public String addTab() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        HoaDon hoaDon = HoaDon.builder().nhanVien(nhanVien).hinhThuc(0).tinhTrang(5).gia(0.0).thanhTien(0.0).taoLuc(LocalDateTime.now()).build();
        hoaDonService.add(hoaDon);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .nguoiTao(nhanVien.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);

        return "redirect:/giao_dich";
    }

    @PostMapping("/add_khachHang_to_hoa_don/{idKH}")
    public String addKhachHangToHoaDon(@RequestParam(value = "idKhachHang",required = false) String idKhachHang,
                                       @PathVariable(value = "idKH", required = false) UUID id,
                                       @ModelAttribute HoaDon hoaDon) {
        HoaDon existingHoaDon = hoaDonRepository.findById(id).orElse(null);
        KhachHang existingKhachHang = khachHangService.getOne(idKhachHang);
        if (existingHoaDon.getKhachHang()==null){
            existingKhachHang.setTen(existingKhachHang.getTen());
            existingHoaDon.setKhachHang(existingKhachHang);
            hoaDonRepository.save(existingHoaDon);
        }
        return "redirect:/giao_dich";
    }

    @GetMapping("/spct")
    @ResponseBody
    public Page<SanPhamCT> paginate(@RequestParam("p") int page) {
        return sanPhamCTService.getBySL(PageRequest.of(page, 5));
    }
    @GetMapping("/deleteTab/")
    public String deleteTab(@RequestParam("id") String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        HoaDon hoaDon = hoaDonService.getOne(UUID.fromString(id));
        hoaDon.setTinhTrang(0);
        hoaDonService.add(hoaDon);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .nguoiTao(nhanVien.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        List<HoaDonChiTiet> list = hoaDonChiTietService.getList(UUID.fromString(id));
        for(HoaDonChiTiet hdct:list){
            SanPhamCT sanPhamCT = sanPhamCTService.getOne(hdct.getSanPhamChiTiet().getId());
            sanPhamCT.setSl(sanPhamCT.getSl()+hdct.getSoLuong());
            sanPhamCTService.add(sanPhamCT);
        }
        if(hoaDon.getPhieuGiamGia()!=null){
            PhieuGiamGia phieuGiamGia = hoaDon.getPhieuGiamGia();
            phieuGiamGia.setSoLuong(phieuGiamGia.getSoLuong()+1);
            phieuGiamGiaRepository.save(phieuGiamGia);
        }
        return "redirect:/giao_dich";
    }

    @GetMapping("/search")
    @ResponseBody
    public Page<SanPhamCT> search(@RequestParam("keyword") String keyword, @RequestParam("p") int page) {
        return sanPhamCTService.search(keyword, PageRequest.of(page, 5));
    }

    @GetMapping("/viewOne/")
    @ResponseBody
    public SanPhamCT viewOne(String id) {
        return sanPhamCTService.getOne(id);
    }

    @GetMapping("/viewOneByMa")
    @ResponseBody
    public SanPhamCT viewOneByMa(@RequestParam("maSPCTSearch") String ma) {
        return sanPhamCTService.getOneByMa(ma);
    }

    @GetMapping("/filter")
    @ResponseBody
    public Page<SanPhamCT> filter(@RequestParam("danhMucSearch") String danhMucId,
                                  @RequestParam("kichThuocSearch") String kichThuocId,
                                  @RequestParam("mauSacSearch") String mauSacId,
                                  @RequestParam("thuongHieuSearch") String thuongHieuId,
                                  @RequestParam("p") int page) {

        return sanPhamCTService.searchByFilter(danhMucId, kichThuocId, mauSacId, thuongHieuId, PageRequest.of(page, 5));
    }
}