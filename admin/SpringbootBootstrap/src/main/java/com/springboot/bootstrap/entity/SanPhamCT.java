package com.springboot.bootstrap.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "san_pham_chi_tiet")
@Getter
@Setter
@Builder
public class SanPhamCT {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_spct")
    private String id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "so_luong_ton")
    private int sl;
    @Column(name = "gia_ban")
    private double gia;
    @Column(name = "anh")
    private byte[] data;
    @Column(name = "trang_thai")
    private int trangThai;
    @Column(name = "tao_luc")
    private LocalDateTime taoLuc;
    @Column(name = "sua_luc")
    private LocalDateTime suaLuc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mau_sac", referencedColumnName = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kich_thuoc", referencedColumnName = "id_kich_thuoc")
    private KichThuoc kichThuoc;
}
