package com.springboot.bootstrap.controller.giohangcontroller;


import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.entity.FormatHelper;
import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.SanPham;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.repository.GioHangChiTietRepository;
import com.springboot.bootstrap.repository.GioHangRepository;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController{

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private Base64Image base64Image;

    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private AnhRepo anhRepo;

    @GetMapping
    public String getAll(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        KhachHang khachHang= khachHangRepository.findById(userDetails.getUsername()).get();
        GioHang gioHang=gioHangRepository.findByKhachHang(khachHang);

        List<GioHangChiTiet> listGioHang=gioHangChiTietRepository.findAllByGioHang(gioHang);
        Map<String, Anh> mapAnhSanPham = new HashMap<>();
        for (GioHangChiTiet ghct : listGioHang) {
            List<Anh> listAnh = anhRepo.findAllBySanPham(ghct.getSanPhamCT().getSanPham());
            Anh anh= listAnh.get(0);
            mapAnhSanPham.put(ghct.getSanPhamCT().getSanPham().getId(), anh);
        }
        model.addAttribute("listGioHangCT",listGioHang);
        model.addAttribute("gioHang",gioHang);
        model.addAttribute("mapAnhSanPham", mapAnhSanPham);
        model.addAttribute("formatHelper", new FormatHelper());
        model.addAttribute("base64Image", base64Image);
        return "/customer/gio-hang";
    }

    @PostMapping("/update-so-luong/{idGhct}")
//    @ResponseBody
    public String updateSoLuong(@PathVariable(value = "idGhct", required = false) UUID idGhct
                                ,@RequestParam(name = "soLuong") int soLuong) {
            GioHangChiTiet gioHangChiTiet=gioHangChiTietRepository.findById(idGhct).orElse(null);
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setDonGia(gioHangChiTiet.getSanPhamCT().getGia()*gioHangChiTiet.getSoLuong());
            gioHangChiTietRepository.save(gioHangChiTiet);
        return "redirect:/gio-hang";
    }

    @GetMapping("/delete/{idGhct}")
    public String deleteItem(Model model,
                             @PathVariable("idGhct") UUID idGhct) {
        gioHangChiTietRepository.deleteById(idGhct);
        return "redirect:/gio-hang";
    }


}

