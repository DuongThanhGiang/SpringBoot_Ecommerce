package com.springboot.bootstrap.controller.nhanviencontroller;

import com.springboot.bootstrap.entity.DTO.ValidateDTO;
import com.springboot.bootstrap.entity.NhanVien;
import com.springboot.bootstrap.service.NhanVienService;
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
@RequestMapping("/nhan_vien")
public class NhanVienRestController {
    @Autowired
    private NhanVienService nhanVienService;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static String regexPhoneNumber = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b";
    @PostMapping("/validateAddNhanVien")
    public ValidateDTO validateAddNhanVien(@RequestBody NhanVien nhanVien){
        if(nhanVien.getTen().isEmpty()||nhanVien.getEmail().isEmpty()||nhanVien.getDiaChi().isEmpty()||nhanVien.getMatKhau().isEmpty()||nhanVien.getSdt().isEmpty()
        ||nhanVien.getNgaySinh()==null){
            return ValidateDTO.builder().success(false).message("Vui lòng điền đủ thông tin").build();
        }else if(!VALID_EMAIL_ADDRESS_REGEX.matcher(nhanVien.getEmail()).matches()){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh email").build();
        }else if(!nhanVien.getSdt().matches(regexPhoneNumber)){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh sdt").build();
        }
        List<NhanVien> listNhanVien = nhanVienService.findAll();
        for(NhanVien nv:listNhanVien){
            if(nv.getEmail().equals(nhanVien.getEmail())){
                return ValidateDTO.builder().success(false).message("Email đã tồn tại").build();
            }
        }
        return ValidateDTO.builder().success(true).build();
    }
    @PostMapping("/validateUpdateNhanVien")
    public ValidateDTO validateUpdateNhanVien(@RequestBody NhanVien nhanVien){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien1 = nhanVienService.getOne(userDetails.getUsername());
        if(nhanVien.getTen().isEmpty()||nhanVien.getEmail().isEmpty()||nhanVien.getDiaChi().isEmpty()||nhanVien.getSdt().isEmpty()
                ||nhanVien.getNgaySinh()==null){
            return ValidateDTO.builder().success(false).message("Vui lòng điền đủ thông tin").build();
        }else if(!VALID_EMAIL_ADDRESS_REGEX.matcher(nhanVien.getEmail()).matches()){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh email").build();
        }else if(!nhanVien.getSdt().matches(regexPhoneNumber)){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh sdt").build();
        }
        List<NhanVien> listNhanVien = nhanVienService.findAll();
        for(NhanVien nv:listNhanVien){
            if(nv.getEmail().equals(nhanVien.getEmail())){
                if(nhanVien.getEmail().equals(nhanVien1.getEmail())){
                    return ValidateDTO.builder().success(true).build();
                }
                return ValidateDTO.builder().success(false).message("Email đã tồn tại").build();
            }
        }
        return ValidateDTO.builder().success(true).build();
    }
}
