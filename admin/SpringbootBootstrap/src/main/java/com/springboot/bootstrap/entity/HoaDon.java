package com.springboot.bootstrap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @Column(name = "id_hoa_don")
    private UUID idHoaDon;
    @ManyToOne
    @JoinColumn(name = "id_kh")
    private KhachHang khachHang;
    @ManyToOne
    @JoinColumn(name = "id_pgg")
    private PhieuGiamGia phieuGiamGia;
    @Column(name = "ma")
    private String ma;
    @Column(name = "gia")
    private Double gia;
    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;
    @Column(name = "ngay_ship")
    private Date ngayShip;
    @Column(name = "ngay_nhan")
    private Date ngayNhan;
    @Column(name = "tinh_trang")
    private Integer tinhTrang;
    @Column(name = "tien_ship")
    private Double tienShip;
    @Column(name = "thanh_tien")
    private Double thanhTien;
    @Column(name = "tao_luc")
    private LocalDateTime taoLuc;
    @Column(name = "sua_luc")
    private Date suaLuc;
    @Column(name = "thanh_pho")
    private String thanhPho;
    @Column(name = "quan_huyen")
    private String quanHuyen;
    @Column(name = "phuong_xa")
    private String phuongXa;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "hinh_thuc")
    private int hinhThuc;
    @Column(name = "sdt")
    private String sdt;
    @Column(name = "ghi_chu")
    private String ghiChu;
}
