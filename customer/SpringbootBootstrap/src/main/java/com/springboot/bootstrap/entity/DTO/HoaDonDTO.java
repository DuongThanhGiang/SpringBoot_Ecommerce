package com.springboot.bootstrap.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HoaDonDTO {
    private String ma;
    private String idNhanVien;
    private String idKhachHang;
    private Double gia;
    private String ngayThanhToan;
    private String tinhTrang;
    private String diaChi;
    private Double tienShip;
    private Double thanhTien;
}
