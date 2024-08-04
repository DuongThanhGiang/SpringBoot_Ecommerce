package com.springboot.bootstrap.controller.hoadoncontroller;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.entity.DTO.HoaDonDTO;
import com.springboot.bootstrap.entity.DanhMuc;

import com.springboot.bootstrap.entity.FormatHelper;
import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.HoaDonTimeline;
import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.entity.NhanVien;
import com.springboot.bootstrap.entity.PhieuGiamGia;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.entity.ThuongHieu;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.HoaDonTLRepo;
import com.springboot.bootstrap.repository.PhieuGiamGiaRepository;
import com.springboot.bootstrap.service.DanhMucService;
import com.springboot.bootstrap.service.KichThuocService;
import com.springboot.bootstrap.service.MauSacService;
import com.springboot.bootstrap.service.NhanVienService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.service.ThuongHieuService;
import com.springboot.bootstrap.service.impl.HoaDonChiTietServiceImpl;
import com.springboot.bootstrap.service.impl.HoaDonServiceImpl;
import com.springboot.bootstrap.utility.Base64Image;
import com.springboot.bootstrap.utility.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/hoa_don")
public class HoaDonController {
    List<String> listTinhTrang = new ArrayList();
    public HoaDonController() {
        listTinhTrang.add("Chờ xác nhận");
        listTinhTrang.add("Chờ lấy hàng");
        listTinhTrang.add("Đang giao");
        listTinhTrang.add("Hoàn thành");
        listTinhTrang.add("Đã huỷ");
    }

    @Autowired
    private HoaDonServiceImpl hoaDonService;

    @Autowired
    private FormatDate formatDate;
    @Autowired
    private HoaDonTLRepo hoaDonTLRepo;
    @Autowired
    private Base64Image base64Image;
    @Autowired
    private AnhRepo anhRepo;
    @Autowired
    private HoaDonChiTietServiceImpl hoaDonChiTietService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private DanhMucService danhMucService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;
    @GetMapping("")
    public String view(Model model,@RequestParam("p") Optional<Integer> p) {
        Page<HoaDon> listTH=hoaDonService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listHoaDon",listTH);
        model.addAttribute("hoaDon",new HoaDonDTO());
        model.addAttribute("listTinhTrang",listTinhTrang);
        model.addAttribute("formatDate",formatDate);
        model.addAttribute("formatHelper",new FormatHelper());
        return "/pages/hoa_don";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(value = "tinhTrang", required = false) Integer tinhTrang,
                         @RequestParam(value = "hinhThuc", required = false) Integer hinhThuc,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "startDate", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                         @RequestParam(value = "endDate", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                         @RequestParam("p") Optional<Integer> p, Model model) {

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (startDate != null) {
            startDateTime = convertToLocalDateTime(startDate);
        }

        if (endDate != null) {
            endDateTime = convertToLocalDateTime(endDate);
        }

        Page<HoaDon> listHD = hoaDonRepository.filterHoaDon(tinhTrang, hinhThuc, keyword, startDateTime, endDateTime, PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listHoaDon", listHD);
        model.addAttribute("hoaDon", new HoaDonDTO());
        model.addAttribute("listTinhTrang", listTinhTrang);
        model.addAttribute("formatDate", formatDate);
        model.addAttribute("formatHelper", new FormatHelper());
        return "/pages/hoa_don";
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @GetMapping("/view/{id}")
    public String viewOne(Model model,@RequestParam("p") Optional<Integer> p, @PathVariable("id") UUID id){
        List<DanhMuc> listDM = danhMucService.findAllByTrangThai();
        List<ThuongHieu> listTH = thuongHieuService.findAllByTrangThai();
        List<KichThuoc> listKT = kichThuocService.findAllByTrangThai();
        List<MauSac> listMS = mauSacService.findAllByTrangThai();
        Page<HoaDonChiTiet>listHoaDonChiTiet= hoaDonChiTietService.getPage(id,PageRequest.of(p.orElse(0), 5));
        Map<String, Anh> mapAnhSanPham = new HashMap<>();
        for (HoaDonChiTiet hdct:listHoaDonChiTiet) {
            List<Anh> listAnh = anhRepo.findAllBySanPham(hdct.getSanPhamChiTiet().getSanPham());
            Anh anh = listAnh.get(0);
            mapAnhSanPham.put(hdct.getSanPhamChiTiet().getSanPham().getId(), anh);
        }
        model.addAttribute("listMS", listMS);
        model.addAttribute("listTH", listTH);
        model.addAttribute("listDM", listDM);
        model.addAttribute("listKT", listKT);
        model.addAttribute("hoaDon",hoaDonService.getOne(id));
        model.addAttribute("formatDate",formatDate);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("base64Image",base64Image);
        model.addAttribute("mapAnhSanPham",mapAnhSanPham);
        model.addAttribute("listHoaDonTL",hoaDonTLRepo.findAllByHoaDonOrderByNgayTaoAsc(hoaDonService.getOne(id)));
        model.addAttribute("listHoaDonChiTiet",listHoaDonChiTiet);
        return "/pages/hoa_don_chi_tiet";
    }
    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> page,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         Model model){
        Page<HoaDon> listHoaDon = hoaDonService.getListSearch(keyword,PageRequest.of(page.orElse(0), 5));
        model.addAttribute("listHoaDon",listHoaDon);
        model.addAttribute("hoaDon",new HoaDonDTO());
        model.addAttribute("listTinhTrang",listTinhTrang);
        return "/pages/hoa_don";
    }
    @GetMapping("/thanh_toan/{idhd}")
    public String thanhToan(@PathVariable("idhd") UUID id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        HoaDon hoaDon = hoaDonService.getOne(id);
        hoaDon.setTinhTrang(4);

        hoaDon.setNgayThanhToan(LocalDateTime.now());
        hoaDonService.add(hoaDon);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .nguoiTao(nhanVien.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        return "redirect:/giao_dich";
    }
    @PostMapping("/confirm/{id}")
    public String confirm(Model model, @PathVariable("id") UUID id, @RequestParam("moTa") String moTa) {
        HoaDon hoaDon=hoaDonService.getOne(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        if(hoaDon.getTinhTrang()!=0){
            hoaDon.setTinhTrang(hoaDon.getTinhTrang()+1);
            if(hoaDon.getTinhTrang()==4){
                hoaDon.setNgayThanhToan(LocalDateTime.now());
            }
            hoaDonService.add(hoaDon);
            HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                    .hoaDon(hoaDon)
                    .moTa(moTa)
                    .nguoiTao(nhanVien.getTen())
                    .trangThai(hoaDon.getTinhTrang())
                    .ngayTao(LocalDateTime.now()).build();
            hoaDonTLRepo.save(hoaDonTimeline);
        }

        return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
    }
    @PostMapping("/confirm/ship/{id}")
    public String confirmShip(Model model, @PathVariable("id") UUID id, @RequestParam("moTa") String moTa,@RequestParam("giaShip") String giaShip) {
        HoaDon hoaDon=hoaDonService.getOne(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        if(hoaDon.getTinhTrang()!=0){
            hoaDon.setTinhTrang(hoaDon.getTinhTrang()+1);
            hoaDon.setTienShip(Double.parseDouble(giaShip));
            hoaDon.setThanhTien(hoaDon.getThanhTien()+hoaDon.getTienShip());
            hoaDonService.add(hoaDon);
            HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                    .hoaDon(hoaDon)
                    .moTa(moTa)
                    .nguoiTao(nhanVien.getTen())
                    .trangThai(hoaDon.getTinhTrang())
                    .ngayTao(LocalDateTime.now()).build();
            hoaDonTLRepo.save(hoaDonTimeline);
        }

        return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
    }
    @PostMapping("/cancel/{id}")
    public String cancel(Model model, @PathVariable("id") UUID id,@RequestParam("moTa") String moTa) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        HoaDon hoaDon=hoaDonService.getOne(id);
        List<HoaDonChiTiet> list = hoaDonChiTietService.getList(id);
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
        hoaDon.setTinhTrang(0);
        hoaDonService.add(hoaDon);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .moTa(moTa)
                .nguoiTao(nhanVien.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
    }
    @PostMapping("/addSPCTTL")
    public String addHdct(@RequestParam(name = "idhd") UUID idhd,
                          @RequestParam(name = "idspct") UUID idspct,
                          @RequestParam(name = "soLuong") Integer soLuong){
        SanPhamCT sanPhamCT = sanPhamCTService.getOne(idspct.toString());
        sanPhamCT.setSl(sanPhamCT.getSl()-soLuong);
        sanPhamCTService.update(sanPhamCT,idspct.toString());
        HoaDon hoaDon = hoaDonService.getOne(idhd);
        List<HoaDonChiTiet> list = hoaDonChiTietService.getList(idhd);
        Pageable pageable= PageRequest.of(0,1);

        if(list!=null){
            for(HoaDonChiTiet hoaDonChiTiet:list){
                if(hoaDonChiTiet.getSanPhamChiTiet().getId().equalsIgnoreCase(idspct.toString())){
                    hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong()+soLuong);
                    hoaDonChiTiet.setGia(hoaDonChiTiet.getSanPhamChiTiet().getGia());//set gia
                    hoaDonChiTietService.update(hoaDonChiTiet);
                    hoaDon.setGia(hoaDon.getGia()+hoaDonChiTiet.getGia()*soLuong);//vd sp 100k thì ở đây giá bằng 100k
                    hoaDon.setThanhTien(hoaDon.getThanhTien()+hoaDonChiTiet.getGia()*soLuong);
                    if(hoaDon.getPhieuGiamGia()==null){
                        hoaDon.setThanhTien(hoaDon.getGia());
                        hoaDonService.add(hoaDon);
                    }else if(hoaDon.getPhieuGiamGia().getDonVi()==2){
                        hoaDon.setThanhTien(hoaDon.getGia()-hoaDon.getPhieuGiamGia().getGiaTriGiam());
                        hoaDonService.add(hoaDon);
                    }else if(hoaDon.getPhieuGiamGia().getDonVi()==1){
                        if(hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa() <= hoaDon.getGia()*(hoaDon.getPhieuGiamGia().getGiaTriGiam()/100)) {
                            hoaDon.setThanhTien(hoaDon.getGia() - hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa());
                        }else{
                            hoaDon.setThanhTien(hoaDon.getGia() * (100 - hoaDon.getPhieuGiamGia().getGiaTriGiam()) / 100);
                        }

                        hoaDonService.add(hoaDon);
                    }

                    return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
                }
            }
        }
        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(sanPhamCT,hoaDon,sanPhamCT.getGia(),soLuong);
        hoaDonChiTietService.add(hoaDonChiTiet);
        hoaDon.setGia(hoaDon.getGia()+hoaDonChiTiet.getGia()*soLuong);

        if(hoaDon.getPhieuGiamGia()==null){
            hoaDon.setThanhTien(hoaDon.getGia());
            hoaDonService.add(hoaDon);
        }else if(hoaDon.getPhieuGiamGia().getDonVi()==2){
            hoaDon.setThanhTien(hoaDon.getGia()-hoaDon.getPhieuGiamGia().getGiaTriGiam());
            hoaDonService.add(hoaDon);
        }else if(hoaDon.getPhieuGiamGia().getDonVi()==1){
            if(hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa() <= hoaDon.getGia()*(hoaDon.getPhieuGiamGia().getGiaTriGiam()/100)) {
                hoaDon.setThanhTien(hoaDon.getGia() - hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa());
            }else{
                hoaDon.setThanhTien(hoaDon.getGia() * (100 - hoaDon.getPhieuGiamGia().getGiaTriGiam()) / 100);
            }

            hoaDonService.add(hoaDon);
        }

        return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
    }
    @GetMapping("/deleteSPCTTL/{idhdct}")
    public String delete(@PathVariable(name = "idhdct") String idhdct){
        Integer soLuong = hoaDonChiTietService.getOne(UUID.fromString(idhdct)).getSoLuong();
        Integer tong = hoaDonChiTietService.getOne(UUID.fromString(idhdct)).getSanPhamChiTiet().getSl();
        SanPhamCT sanPhamCT = hoaDonChiTietService.getOne(UUID.fromString(idhdct)).getSanPhamChiTiet();
        sanPhamCT.setSl(tong+soLuong);
        sanPhamCTService.update(sanPhamCT,sanPhamCT.getId());
        HoaDon hoaDon = hoaDonChiTietService.getOne(UUID.fromString(idhdct)).getHoaDon();

        hoaDon.setGia(hoaDon.getGia()-hoaDonChiTietService.getOne(UUID.fromString(idhdct)).getGia()*soLuong);
        Pageable pageable= PageRequest.of(0,1);

        if(hoaDon.getPhieuGiamGia()==null){
            hoaDon.setThanhTien(hoaDon.getGia());
            hoaDonService.add(hoaDon);
        }else if(hoaDon.getPhieuGiamGia().getDonVi()==2){
            hoaDon.setThanhTien(hoaDon.getGia()-hoaDon.getPhieuGiamGia().getGiaTriGiam());
            hoaDonService.add(hoaDon);
        }else if(hoaDon.getPhieuGiamGia().getDonVi()==1){
            if(hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa() <= hoaDon.getGia()*(hoaDon.getPhieuGiamGia().getGiaTriGiam()/100)) {
                hoaDon.setThanhTien(hoaDon.getGia() - hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa());
            }else{
                hoaDon.setThanhTien(hoaDon.getGia() * (100 - hoaDon.getPhieuGiamGia().getGiaTriGiam()) / 100);
            }
            hoaDonService.add(hoaDon);
        }
        hoaDonService.add(hoaDon);
        hoaDonChiTietService.delete(UUID.fromString(idhdct));

        return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
    }
    @PostMapping("/updateSPCTTL/{idhdct}")
    public String update(@PathVariable(name = "idhdct") UUID idhdct,
                         @RequestParam(name = "soLuong") Integer soLuong){
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.getOne(idhdct);
        HoaDon hoaDon = hoaDonChiTietService.getOne(idhdct).getHoaDon();
        Pageable pageable= PageRequest.of(0,1);


        SanPhamCT sanPhamCT = hoaDonChiTiet.getSanPhamChiTiet();
        sanPhamCT.setSl(sanPhamCT.getSl()+hoaDonChiTiet.getSoLuong()-soLuong);
        sanPhamCTService.update(sanPhamCT,sanPhamCT.getId());
        hoaDon.setGia(hoaDon.getGia()-hoaDonChiTiet.getGia()*hoaDonChiTiet.getSoLuong());
        hoaDonChiTiet.setSoLuong(soLuong);
        hoaDonChiTiet.setGia(hoaDonChiTiet.getSanPhamChiTiet().getGia());
        hoaDon.setGia(hoaDon.getGia()+hoaDonChiTiet.getGia()*soLuong);
        if(hoaDon.getPhieuGiamGia()==null){
            hoaDon.setThanhTien(hoaDon.getGia());
            hoaDonService.add(hoaDon);
        }else if(hoaDon.getPhieuGiamGia().getDonVi()==2){
            hoaDon.setThanhTien(hoaDon.getGia()-hoaDon.getPhieuGiamGia().getGiaTriGiam());
            hoaDonService.add(hoaDon);
        }else if(hoaDon.getPhieuGiamGia().getDonVi()==1){
            if(hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa() <= hoaDon.getGia()*(hoaDon.getPhieuGiamGia().getGiaTriGiam()/100)) {
                hoaDon.setThanhTien(hoaDon.getGia() - hoaDon.getPhieuGiamGia().getGiaTriGiamToiDa());
            }else{
                hoaDon.setThanhTien(hoaDon.getGia() * (100 - hoaDon.getPhieuGiamGia().getGiaTriGiam()) / 100);
            }
            hoaDonService.add(hoaDon);
        }
        hoaDonService.add(hoaDon);
        hoaDonChiTietService.update(hoaDonChiTiet);

        return "redirect:/hoa_don/view/"+hoaDon.getIdHoaDon();
    }
}
