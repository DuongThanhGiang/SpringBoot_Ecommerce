package com.springboot.bootstrap.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dia_chi")
public class DiaChi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_dia_chi")
    private String idDiaChi;

//    @NotEmpty(message = "Không Được Để Trống Địa Chỉ")
    @Column(name = "dia_chi")
    private String diaChiCuThe;

//    @NotEmpty(message = "Không Được Để Trống Huyện")
    @Column(name = "quan_huyen")
    private String quan_huyen;

//    @NotEmpty(message = "Không Được Để Trống Tỉnh")
    @Column(name = "tinh_thanh")
    private String tinh_thanh;

//    @NotEmpty(message = "Không Được Để Trống Thành Phố")
    @Column(name = "phuong_xa")
    private String phuong_xa;

//    @NotEmpty(message = "Không Được Để Trống Quốc Gia")
    @Column(name = "quoc_gia")
    private String quocGia;

//    @Pattern(message = "Nhập số Điện Thoại Chưa Đúng", regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")
//    @NotEmpty(message = "Không Được Để Trống Số Điện Thoại")
//    @Size(min = 10, message = "Số Điện Thoại Tối Thiểu 10 Số")
//    @Size(max = 10, message = "Số Điện Thoại Tối Đa 10 Số")
    @Column(name = "sdt")
    private String sdt;

    @Column(name = "trang_thai")
    private int trang_thai;

    @Column(name = "tao_luc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.sql.Timestamp taoLuc;

    @Column(name = "sua_luc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.sql.Timestamp suaLuc;

    @Column(name = "tao_boi")
    private String taoBoi;

    @Column(name = "sua_boi")
    private String suaBoi;

    @Override
    public int hashCode() {
        return 42;
    }
}
