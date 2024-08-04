package com.springboot.bootstrap.controller.hoadoncontroller;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.entity.FormatHelper;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.HoaDonTimeline;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.PhieuGiamGia;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.HoaDonTLRepo;
import com.springboot.bootstrap.repository.PhieuGiamGiaRepository;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.utility.Base64Image;
import com.springboot.bootstrap.utility.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/hoa_don")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private FormatDate formatDate;
    @Autowired
    private HoaDonTLRepo hoaDonTLRepo;
    @Autowired
    private AnhRepo anhRepo;
    @Autowired
    private Base64Image base64Image;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;
    @GetMapping("/getListHoaDon")
    public String getListHoaDon(Model model, @RequestParam("p") Optional<Integer> p){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        List<HoaDon> list = hoaDonService.getListHoaDon(khachHang);
        Page<HoaDon> page = new PageImpl(list, PageRequest.of(p.orElse(0), 5), list.size());
        model.addAttribute("listHoaDon",page);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("formatDate",formatDate);
        return "/customer/danh-sach-hoa-don";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(value = "tinhTrang", required = false) Integer tinhTrang,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "startDate", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                         @RequestParam(value = "endDate", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                         @RequestParam("p") Optional<Integer> p, Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (startDate != null) {
            startDateTime = convertToLocalDateTime(startDate);
        }

        if (endDate != null) {
            endDateTime = convertToLocalDateTime(endDate);
        }

        List<HoaDon> list = hoaDonRepository.filterHoaDon(tinhTrang, keyword, startDateTime, endDateTime,khachHang.getIdKhachHang(), PageRequest.of(p.orElse(0), 5));
        Page<HoaDon> page = new PageImpl(list, PageRequest.of(p.orElse(0), 5), list.size());
        model.addAttribute("listHoaDon",page);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("formatDate",formatDate);
        return "/customer/danh-sach-hoa-don";
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @GetMapping("/view/{id}")
    public String viewOne(Model model,@RequestParam("p") Optional<Integer> p, @PathVariable("id") UUID id){
        model.addAttribute("hoaDon",hoaDonService.getOne(id));
        List<HoaDonChiTiet> list = hoaDonChiTietService.getListHoaDonChiTiet(hoaDonService.getOne(id));
        Map<String, Anh> mapAnhSanPham = new HashMap<>();
        for (HoaDonChiTiet hdct : list) {
            List<Anh> listAnh = anhRepo.findAllBySanPham(hdct.getSanPhamChiTiet().getSanPham());
            Anh anh = listAnh.get(0);
            mapAnhSanPham.put(hdct.getSanPhamChiTiet().getSanPham().getId(), anh);
        }
        Page<HoaDonChiTiet> page = new PageImpl(list,PageRequest.of(p.orElse(0), 5),list.size());
        model.addAttribute("listHoaDonChiTiet",page);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("listHoaDonTL",hoaDonTLRepo.findAllByHoaDonOrderByNgayTaoAsc(hoaDonService.getOne(id)));
        model.addAttribute("formatDate",formatDate);
        model.addAttribute("mapAnhSanPham",mapAnhSanPham);
        model.addAttribute("base64Image",base64Image);
        return "/customer/hoa-don-chi-tiet";
    }
    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> p,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        List<HoaDon> list = hoaDonService.getListSearch(keyword,khachHang);
        Page<HoaDon> hoaDonPage = new PageImpl(list,PageRequest.of(p.orElse(0), 5), list.size());
        model.addAttribute("listHoaDon",hoaDonPage);
        return "/pages/hoa_don";
    }
    @PostMapping("/xac_nhan")
    public String xacNhan(@RequestParam("ghiChu") String ghiChu,
                          @RequestParam("idHoaDon") UUID idHoaDon){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        HoaDon hoaDon = hoaDonService.getOne(idHoaDon);
        hoaDon.setTinhTrang(4);
        hoaDon.setNgayThanhToan(LocalDateTime.now());
        hoaDonService.save(hoaDon);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .moTa(ghiChu)
                .nguoiTao(khachHang.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        return "redirect:/hoa_don/view/"+idHoaDon;
    }
    @PostMapping("/huy")
    public String huy(@RequestParam("ghiChuHuy") String ghiChu,
                      @RequestParam("idHoaDon") UUID idHoaDon){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        HoaDon hoaDon = hoaDonService.getOne(idHoaDon);
        List<HoaDonChiTiet> list = hoaDonChiTietService.getListHoaDonChiTiet(hoaDon);
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
        hoaDonService.save(hoaDon);
        HoaDonTimeline hoaDonTimeline= HoaDonTimeline.builder()
                .hoaDon(hoaDon)
                .moTa(ghiChu)
                .nguoiTao(khachHang.getTen())
                .trangThai(hoaDon.getTinhTrang())
                .ngayTao(LocalDateTime.now()).build();
        hoaDonTLRepo.save(hoaDonTimeline);
        return "redirect:/hoa_don/view/"+idHoaDon;
    }
}
