package com.springboot.bootstrap.controller.thanhtoancontroller;

import com.springboot.bootstrap.entity.*;
import com.springboot.bootstrap.entity.DTO.ValidateDTO;
import com.springboot.bootstrap.repository.PhieuGiamGiaRepository;
import com.springboot.bootstrap.service.GioHangChiTietService;
import com.springboot.bootstrap.service.GioHangService;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/thanh_toan")
public class ThanhToanShopRestController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    private static String regexPhoneNumber = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b";

    @GetMapping("/select-voucher")
    public ResponseEntity<PhieuGiamGia> selectVoucher(@RequestParam(value = "maVoucher", required = false)  String maVoucher){
        PhieuGiamGia phieuGiamGia= phieuGiamGiaRepository.findByMa(maVoucher);
        if (phieuGiamGia != null) {
            return ResponseEntity.ok(phieuGiamGia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/validateThanhToan")
    public ValidateDTO validate(@RequestBody HoaDon hdc){
        if(hdc.getThanhPho().isEmpty()||hdc.getSdt()==null||hdc.getQuanHuyen()==null||hdc.getPhuongXa()==null||hdc.getQuanHuyen().isEmpty()||hdc.getPhuongXa().isEmpty()||hdc.getDiaChi().isEmpty()){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đầy đủ dữ liệu" ).build();
        } else if(!hdc.getSdt().matches(regexPhoneNumber)){
            return ValidateDTO.builder().success(false).message("Vui lòng nhập đúng định dạnh sdt").build();}
        Double sumMoney=0.0;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KhachHang khachHang = khachHangService.getOne(userDetails.getUsername());
        GioHang gioHang = gioHangService.getIdByIdKh(khachHang);
        List<GioHangChiTiet> list = gioHangChiTietService.getListGhct(gioHangService.getIdByIdKh(khachHang));
        for(GioHangChiTiet gioHangChiTiet:list){
            SanPhamCT sanPhamCT = gioHangChiTiet.getSanPhamCT();
            if(sanPhamCT.getSl()<gioHangChiTiet.getSoLuong()){
                return ValidateDTO.builder().success(false).message("Sản phẩm " +sanPhamCT.getMa()+" bị vượt quá số lượng, vui lòng thử lại" ).build();
            }else {
                sumMoney+=gioHangChiTiet.getSoLuong()*sanPhamCT.getGia();
            }
        }
        if(gioHang.getThanhTien().doubleValue()!=sumMoney){
            return ValidateDTO.builder().success(false).message("Có sản phẩm đã bị thay đổi giá, vui lòng thử lại" ).build();
        }
        return ValidateDTO.builder().success(true).build();
    }
}
