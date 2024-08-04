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
@Table(name = "gio_hang")
@Builder
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_gio_hang")
    private UUID idGioHang;
    @ManyToOne
    @JoinColumn(name = "id_kh")
    private KhachHang khachHang;
    @Column(name = "ma")
    private String ma;
    @Column(name = "thanh_tien")
    private Double thanhTien;
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
