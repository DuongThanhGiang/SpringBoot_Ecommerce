package com.springboot.bootstrap.controller.sanphamcontroller;


import com.springboot.bootstrap.entity.DTO.SPCTValidMSAndKTDTO;
import com.springboot.bootstrap.entity.FormatHelper;
import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.entity.SanPham;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.DanhMucService;
import com.springboot.bootstrap.service.KichThuocService;
import com.springboot.bootstrap.service.MauSacService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.service.SanPhamService;
import com.springboot.bootstrap.service.ThuongHieuService;
import com.springboot.bootstrap.utility.Base64Image;
import com.springboot.bootstrap.utility.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/spct")
public class SPCTController {
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private DanhMucService danhMucService;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Autowired
    private Base64Image base64Image;

    @GetMapping("/viewDetail/{id}")
    public String getAll(@PathVariable("id") String idSP, @RequestParam("p") Optional<Integer> p, Model model) {
        Page<SanPhamCT> listSPCT = sanPhamCTService.getAllBySP(idSP, PageRequest.of(p.orElse(0), 5));
        List<KichThuoc> listKT = kichThuocService.findAllByTrangThai();
        List<MauSac> listMS = mauSacService.findAllByTrangThai();
        model.addAttribute("idSP", idSP);
        model.addAttribute("listKT", listKT);
        model.addAttribute("listMS", listMS);
        model.addAttribute("listSPCT", listSPCT);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("qrCodeGenerator", qrCodeGenerator);
        return "/pages/spct";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "kt",required = false) String idKT,
                         @RequestParam(value = "ms",required = false) String idMS,
                         @RequestParam(value = "sp",required = false) String idSP,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam("p") Optional<Integer> p, Model model) {
        Page<SanPhamCT> listSPCT = sanPhamCTRepo.filter(idKT,idMS,idSP,keyword, PageRequest.of(p.orElse(0), 5));
        List<KichThuoc> listKT = kichThuocService.findAllByTrangThai();
        List<MauSac> listMS = mauSacService.findAllByTrangThai();
        model.addAttribute("idSP", idSP);
        model.addAttribute("listKT", listKT);
        model.addAttribute("listMS", listMS);
        model.addAttribute("listSPCT", listSPCT);
        model.addAttribute("formatHelper",new FormatHelper());
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("qrCodeGenerator", qrCodeGenerator);
        return "/pages/spct";
    }


    @PostMapping("/addSPCT")
    public String addSanPham(@ModelAttribute("spcta") SanPhamCT sanPhamCT,
                             @RequestParam("idSPAdd") String idSP,
                             @RequestParam("kichThuocAdd") String idKT,
                             @RequestParam("mauSacAdd") String idMS,
                             @RequestParam("donGiaAdd") String donGia,
                             @RequestParam("soLuongAdd") String soLuong) {

        sanPhamCT = SanPhamCT.builder()

                .sanPham(SanPham.builder().id(idSP).build())
                .mauSac(MauSac.builder().id(idMS).build())

                .kichThuoc(KichThuoc.builder().id(idKT).build())
                .sl(Integer.parseInt(soLuong))
                .taoLuc(LocalDateTime.now())
                .gia(Double.parseDouble(donGia)).build();
        sanPhamCTService.add(sanPhamCT);




        return "redirect:/spct/viewDetail/" + idSP;
    }

    @PostMapping("/updateSPCT")
    public String update(@ModelAttribute("spctu") SanPhamCT sanPhamCT,
                         @RequestParam("maSPCTUpd") String maSP,
                         @RequestParam("idSPCTUpd") String idSPCT,
                         @RequestParam("idSPUpd") String idSP,
                         @RequestParam("ktUpd") String idKT,
                         @RequestParam("msUpd") String idMS,
                         @RequestParam("donGiaUpd") String donGia,
                         @RequestParam("soLuongUpd") String soLuong,
                         @RequestParam("tgTao") String tgTao) {

        sanPhamCT = SanPhamCT.builder()
                .ma(maSP)
                .sanPham(SanPham.builder().id(idSP).build())
                .mauSac(MauSac.builder().id(idMS).build())
                .kichThuoc(KichThuoc.builder().id(idKT).build())
                .sl(Integer.parseInt(soLuong))
                .taoLuc(LocalDateTime.parse(tgTao))
                .suaLuc(LocalDateTime.now())
                .gia(Double.parseDouble(donGia)).build();

        sanPhamCTService.update(sanPhamCT, idSPCT);
        return "redirect:/spct/viewDetail/" + idSP;
    }


    @GetMapping("/viewOne/")
    @ResponseBody
    public SanPhamCT viewUpdate(String id) {

        return sanPhamCTService.getOne(id);
    }



    @PostMapping("/checkMSAndKTSPCT")
    public @ResponseBody
    boolean checkProductDetailExistence(@RequestBody SPCTValidMSAndKTDTO request) {
        String idKT = request.getIdKT();
        String idMS = request.getIdMS();
        String idSP = request.getIdSP();

        SanPhamCT sanPhamCT1 = sanPhamCTService.getByMSAndKT(idKT, idMS, idSP);
        return sanPhamCT1 != null;
    }

}
