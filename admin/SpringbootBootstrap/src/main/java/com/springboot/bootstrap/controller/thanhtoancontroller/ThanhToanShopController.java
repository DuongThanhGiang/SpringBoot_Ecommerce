package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.*;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.HoaDonTLRepo;
import com.springboot.bootstrap.repository.PhieuGiamGiaRepository;
import com.springboot.bootstrap.service.GioHangChiTietService;
import com.springboot.bootstrap.service.GioHangService;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/thanh_toan")
public class ThanhToanShopController {
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private HoaDonTLRepo hoaDonTLRepo;
    @Autowired
    private AnhRepo anhRepo;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;
    @Autowired
    private Base64Image base64Image;
    @GetMapping("")
    public String view(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GioHang gioHang = gioHangService.getIdByIdKh(khachHangService.getOne(userDetails.getUsername()));
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHang);
          Map<String, Anh> mapAnhSanPham = new HashMap<>();
        for (GioHangChiTiet ghct : list) {
            List<Anh> listAnh = anhRepo.findAllBySanPham(ghct.getSanPhamCT().getSanPham());
            Anh anh = listAnh.get(0);
            mapAnhSanPham.put(ghct.getSanPhamCT().getSanPham().getId(), anh);
        }
        List<PhieuGiamGia> listVoucher = phieuGiamGiaRepository.findAllByTrangThaiAndGiaTriToiThieuLessThanEqual(1, gioHang.getThanhTien());
        FormatHelper formatHelper=new FormatHelper();
        model.addAttribute("formatHelper", formatHelper);
        model.addAttribute("listVoucher", listVoucher);
        model.addAttribute("listGioHangCt", list);
        model.addAttribute("mapAnhSanPham", mapAnhSanPham);
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("giohang",gioHang);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("kh",khachHangService.getOne(userDetails.getUsername()));
        return "/customer/thanh-toan";
    }

    @PostMapping("/create_hoa_don")
    public String create(@RequestParam("thanhPho") String thanhPho,
                         @RequestParam("quanHuyen") String quanHuyen,
                         @RequestParam("phuongXa") String phuongXa,
                         @RequestParam("diaChi") String diaChi,
                         @RequestParam("ghiChu") String ghiChu,
                         @RequestParam("sdt") String sdt,
                         @RequestParam("voucher") String voucher){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        GioHang gioHang = gioHangService.getIdByIdKh(khachHang);
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHangService.getIdByIdKh(khachHang));
        UUID uuid = UUID.randomUUID();
        PhieuGiamGia phieuGiamGia=phieuGiamGiaRepository.findByMa(voucher);

        Double thanhTienAdd=gioHang.getThanhTien();
        //thanh tien=gia - gia giam
        if (phieuGiamGia!=null){
            phieuGiamGia.setSoLuong(phieuGiamGia.getSoLuong()-1);
            if (phieuGiamGia.getDonVi()==1){
                thanhTienAdd=gioHang.getThanhTien()*((100-phieuGiamGia.getGiaTriGiam())/100);
                if (gioHang.getThanhTien()*(phieuGiamGia.getGiaTriGiam()/100)>phieuGiamGia.getGiaTriGiamToiDa()){
                    thanhTienAdd=gioHang.getThanhTien()-phieuGiamGia.getGiaTriGiamToiDa();
                }
            }
            if (phieuGiamGia.getDonVi()==2){
                thanhTienAdd=gioHang.getThanhTien()-phieuGiamGia.getGiaTriGiam();
            }
        }
        hoaDonService.add(HoaDon.builder().sdt(sdt).idHoaDon(uuid).taoLuc(LocalDateTime.now()).khachHang(khachHang).phieuGiamGia(phieuGiamGia).gia(gioHang.getThanhTien()).tinhTrang(1).thanhTien(0.0).thanhPho(thanhPho).quanHuyen(quanHuyen).phuongXa(phuongXa).diaChi(diaChi).ghiChu(ghiChu).hinhThuc(1).thanhTien(thanhTienAdd).build());
        HoaDon hoaDon = hoaDonService.getOne(uuid);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .moTa(ghiChu)
                .nguoiTao(khachHang.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        for(GioHangChiTiet gioHangChiTiet:list){
            hoaDonChiTietService.add(HoaDonChiTiet.builder().hoaDon(hoaDon).sanPhamChiTiet(gioHangChiTiet.getSanPhamCT()).gia(gioHangChiTiet.getSanPhamCT().getGia()).soLuong(gioHangChiTiet.getSoLuong()).build());
            SanPhamCT sanPhamCT = gioHangChiTiet.getSanPhamCT();
            sanPhamCT.setSl(sanPhamCT.getSl()-gioHangChiTiet.getSoLuong());
            sanPhamCTService.save(sanPhamCT);
        }
        gioHangChiTietService.deleteAllByGioHang(gioHang);
        gioHang.setThanhTien(0.0);
        gioHangService.update(gioHang);
        return "redirect:/thanh_toan";
    }
}
