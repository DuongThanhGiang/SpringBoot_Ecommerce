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
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_hoa_don_chi_tiet")
    private UUID idHoaDonChiTiet;
    @ManyToOne
    @JoinColumn(name = "id_spct")
    private SanPhamCT sanPhamChiTiet;
    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;
    @Column(name = "gia")
    private Double gia;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "tao_luc")
    private Date taoLuc;
    @Column(name = "sua_luc")
    private Date suaLuc;

    public HoaDonChiTiet(SanPhamCT sanPhamChiTiet,HoaDon hoaDon, Double gia, Integer soLuong) {
        this.sanPhamChiTiet = sanPhamChiTiet;
        this.hoaDon = hoaDon;
        this.gia = gia;
        this.soLuong = soLuong;
    }
}
