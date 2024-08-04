package com.springboot.bootstrap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "phieu_giam_gia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class PhieuGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_pgg;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "don_vi")
    private Integer donVi;

    @Column(name = "gia_tri_giam")
    private Double giaTriGiam;

    @Column(name = "gia_tri_toi_thieu")
    private Double giaTriToiThieu;

    @Column(name = "gia_tri_giam_toi_da")
    private Double giaTriGiamToiDa;

    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "tao_luc")
    private Date taoLuc;

    @Column(name = "sua_luc")
    private Date suaLuc;

    @Column(name = "tao_boi")
    private String taoBoi;

    @Column(name = "sua_boi")
    private String suaBoi;

}
