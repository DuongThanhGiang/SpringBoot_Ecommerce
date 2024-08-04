package com.springboot.bootstrap.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.SecureRandom;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_kh")
    private String idKH;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_sinh")
    private java.sql.Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh;

//    @NotEmpty(message = "Không Được Để Trống Email")
//    @Email(message = "Email Chưa Đúng Định Dạng")
    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
//    @Length(min = 8, message = "*Mật khẩu phải có ít nhất 8 ký tự")
//    @NotEmpty(message = "*Không Được Để Trống Mật Khẩu")
    private String matKhau;

    @Column(name = "trang_thai")
    private int trangThai;

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

//    @Pattern(message = "Nhập số Điện Thoại Chưa Đúng", regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")
//    @NotEmpty(message = "Không Được Để Trống Số Điện Thoại")
//    @Size(min = 10, message = "Số Điện Thoại Tối Thiểu 10 Số")
//    @Size(max = 10, message = "Số Điện Thoại Tối Đa 10 Số")
    @Column(name = "sdt")
    private String sdt;

}
