package com.springboot.bootstrap.controller.logincontroller;

import com.google.zxing.qrcode.decoder.Mode;
import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.service.GioHangService;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.UUID;

@Controller
@RequestMapping("/khach_hang")
public class KhachHangController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "/customer/registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@RequestParam("ten") String ten,
                                      @RequestParam("email") String email,
                                      @RequestParam("matKhau") String matKhau,
                                      @RequestParam("ngaySinh") Date ngaySinh,
                                      @RequestParam("gioiTinh") Integer gioiTinh,
                                      @RequestParam("sdt") String sdt) {
        KhachHang khachHang = KhachHang.builder().trangThai(1).sdt(sdt).ngaySinh(ngaySinh).gioiTinh(gioiTinh).ten(ten).email(email).matKhau(passwordEncoder.encode(matKhau)).build();
        khachHangService.save(khachHang);
        GioHang gioHang = GioHang.builder().khachHang(khachHang).thanhTien(0.0).build();
        gioHangService.update(gioHang);
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String view(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        model.addAttribute("kh",khachHang);
        return "/customer/profile";
    }
    @PostMapping("/update")
    public String registerUserAccount(@RequestParam("ten") String ten,
                                      @RequestParam("email") String email,
                                      @RequestParam("ngaySinh") Date ngaySinh,
                                      @RequestParam("sdt") String sdt) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        khachHang.setTen(ten);
        khachHang.setEmail(email);
        khachHang.setNgaySinh(ngaySinh);
        khachHang.setSdt(sdt);
        khachHangService.save(khachHang);
        return "redirect:/";
    }
    @GetMapping("/changePassword")
    public String viewChangePassword(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        model.addAttribute("kh",khachHang);
        return "/customer/change-password";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("matKhauMoi") String matKhauMoi){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        khachHang.setMatKhau(passwordEncoder.encode(matKhauMoi));
        khachHangService.save(khachHang);
        return "redirect:/login";
    }
}
