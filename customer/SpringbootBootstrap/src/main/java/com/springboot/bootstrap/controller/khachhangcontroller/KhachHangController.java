package com.springboot.bootstrap.controller.khachhangcontroller;

import com.springboot.bootstrap.entity.DiaChi;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.service.DiaChiService;
import com.springboot.bootstrap.service.KhachHangService;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/khach_hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model) {
        Page<KhachHang> listKH = khachHangService.getAll(PageRequest.of(p.orElse(0), 5));
        model.addAttribute("listKH", listKH);
        return "/pages/khach_hang";
    }

    @GetMapping("/viewOne/{id}")
    @ResponseBody
    public KhachHang viewUpdate(@PathVariable("id") String idKH) {
        return khachHangService.getOne(idKH);
    }

    @PostMapping("/add")
    public String add(@RequestParam("ten") String ten,
                      @RequestParam("email") String email,
                      @RequestParam("matKhau") String matKhau,
                      @RequestParam("ngaySinh") Date ngaySinh,
                      @RequestParam("gioiTinh") Integer gioiTinh,
                      @RequestParam("sdt") String sdt) {
        KhachHang khachHang = KhachHang.builder().ten(ten).trangThai(1).gioiTinh(gioiTinh).ngaySinh(ngaySinh).matKhau(passwordEncoder.encode(matKhau)).email(email).sdt(sdt).build();
        khachHangService.add(khachHang);
        return "redirect:/khach_hang";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id_khach_hang") String idKH,
                         @RequestParam("ten") String ten,
                         @RequestParam("ngaySinh") Date ngaySinh,
                         @RequestParam("gioiTinh") Integer gioiTinh,
                         @RequestParam("email") String email,
                         @RequestParam("sdt") String sdt,
                         @Nullable @RequestParam(name = "trangThai") Object trangThai) {
        KhachHang khachHang = khachHangService.getOne(idKH);
        khachHang.setTen(ten);
        khachHang.setEmail(email);
        khachHang.setSdt(sdt);
        khachHang.setNgaySinh(ngaySinh);
        khachHang.setGioiTinh(gioiTinh);
        khachHang.setTrangThai(trangThai!=null?1:0);
        khachHangService.update(khachHang);
        return "redirect:/khach_hang";
    }

    @GetMapping("/search")
    public String search(@RequestParam("p") Optional<Integer> page,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         Model model) {
        Page<KhachHang> listKH = khachHangService.searchByEmail(keyword, PageRequest.of(page.orElse(0), 5));
        model.addAttribute("listKH", listKH);
        return "/pages/khach_hang";
    }
}
