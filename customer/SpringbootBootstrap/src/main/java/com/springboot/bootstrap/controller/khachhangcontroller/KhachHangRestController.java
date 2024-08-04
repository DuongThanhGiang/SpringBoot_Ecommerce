package com.springboot.bootstrap.controller.khachhangcontroller;

import com.springboot.bootstrap.entity.DTO.ValidateDTO;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;
@RestController
@RequestMapping("/khach_hang")
public class KhachHangRestController {
    @Autowired
    private KhachHangService khachHangService;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static String regexPhoneNumber = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b";
    @PostMapping("/validateAddKhachHang")
    public ValidateDTO validateAddkhachHang(@RequestBody KhachHang khachHang){
        if(khachHang.getTen().isEmpty()||khachHang.getEmail()==null||khachHang.getEmail().isEmpty()||khachHang.getMatKhau().isEmpty()||khachHang.getSdt().isEmpty()
                ||khachHang.getNgaySinh()==null){
            return ValidateDTO.builder().success(false).message("Vui lòng điền đủ thông tin").build();
        }else if(!VALID_EMAIL_ADDRESS_REGEX.matcher(khachHang.getEmail()).matches()){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh email").build();
        }else if(!khachHang.getSdt().matches(regexPhoneNumber)){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh sdt").build();
        }
        List<KhachHang> listkhachHang = khachHangService.findAll();
        for(KhachHang nv:listkhachHang){
            if(nv.getEmail().equals(khachHang.getEmail())){
                return ValidateDTO.builder().success(false).message("Email đã tồn tại").build();
            }
        }
        return ValidateDTO.builder().success(true).build();
    }
    @PostMapping("/validateUpdateKhachHang")
    public ValidateDTO validateUpdatekhachHang(@RequestBody KhachHang khachHang){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang1 = khachHangService.getOne(userDetails.getUsername());
        if(khachHang.getTen().isEmpty()||khachHang.getEmail()==null||khachHang.getEmail().isEmpty()||khachHang.getSdt().isEmpty()
                ||khachHang.getNgaySinh()==null){
            return ValidateDTO.builder().success(false).message("Vui lòng điền đủ thông tin").build();
        }else if(!VALID_EMAIL_ADDRESS_REGEX.matcher(khachHang.getEmail()).matches()){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh email").build();
        }else if(!khachHang.getSdt().matches(regexPhoneNumber)){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh sdt").build();
        }
        List<KhachHang> listkhachHang = khachHangService.findAll();
        for(KhachHang nv:listkhachHang){
            if(nv.getEmail().equals(khachHang.getEmail())){
                if(khachHang.getEmail().equals(khachHang1.getEmail())){
                    return ValidateDTO.builder().success(true).build();
                }
                return ValidateDTO.builder().success(false).message("Email đã tồn tại").build();
            }
        }
        return ValidateDTO.builder().success(true).build();
    }
}
