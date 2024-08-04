package com.springboot.bootstrap.controller.hoadoncontroller;

import com.springboot.bootstrap.entity.DTO.GioHangDTO;
import com.springboot.bootstrap.entity.DTO.GioHangTqDTO;
import com.springboot.bootstrap.entity.DTO.ResponseSanPhamQrDTO;
import com.springboot.bootstrap.entity.DTO.SanPhamQrDTO;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import com.springboot.bootstrap.service.HoaDonService;
import com.springboot.bootstrap.service.SanPhamCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("")
public class HoaDonChiTietRestController {
    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @PostMapping("/validate")
    public ResponseSanPhamQrDTO validate(@RequestBody SanPhamQrDTO sanPhamQrDTO){
        Integer soLuong = sanPhamCTService.getOne(sanPhamQrDTO.getIdspct().toString()).getSl();
        if(sanPhamQrDTO.getIdhd()==null||sanPhamQrDTO.getSoLuong()==null||sanPhamQrDTO.getIdspct()==null){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Nhập sai kiểu");
            return response;
        }else if(sanPhamQrDTO.getIdhd().toString().isEmpty()||sanPhamQrDTO.getIdspct().toString().isEmpty()){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Không được để trống");
            return response;
        }else if(sanPhamQrDTO.getSoLuong()<=0){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Nhập số lượng lớn hơn 0");
            return response;
        }else if(sanPhamQrDTO.getSoLuong()>soLuong){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Chỉ còn "+soLuong+" sản phẩm");
            return response;
        }else {
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(200,"");
            return response;
        }
    }
    @PostMapping("/validate1")
    public ResponseSanPhamQrDTO validate1(@RequestBody GioHangTqDTO gioHangDTO){
        Integer soLuong = sanPhamCTService.getOneByMa(gioHangDTO.getMa()).getSl();
        Integer soLuongGh = hoaDonChiTietService.getOne(gioHangDTO.getIdHoaDonChiTiet()).getSoLuong();
        if(gioHangDTO.getSoLuong()==null){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Nhập sai kiểu");
            return response;
        }else if(gioHangDTO.getSoLuong()<=0){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Nhập số lượng lớn hơn 0");
            return response;
        }else if(gioHangDTO.getSoLuong()>soLuong+soLuongGh){
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(400,"Chỉ còn "+soLuong+" sản phẩm");
            return response;
        }else {
            ResponseSanPhamQrDTO response = new ResponseSanPhamQrDTO(200,"");
            return response;
        }
    }
}
