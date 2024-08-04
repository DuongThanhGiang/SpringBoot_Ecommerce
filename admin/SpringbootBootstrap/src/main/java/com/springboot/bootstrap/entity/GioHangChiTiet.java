package com.springboot.bootstrap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gio_hang_chi_tiet")
@Builder
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ghct")
    private UUID idGhct;
    @ManyToOne
    @JoinColumn(name = "id_spct")
    private SanPhamCT sanPhamCT;
    @ManyToOne
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "trang_thai")
    private Integer trangThai;
    @Column(name = "don_gia")
    private Double donGia;
    @Column(name = "tao_luc")
    private Date taoLuc;
    @Column(name = "sua_luc")
    private Date suaLuc;
    @Column(name = "tao_boi")
    private String taoBoi;
    @Column(name = "sua_boi")
    private String suaBoi;
}
