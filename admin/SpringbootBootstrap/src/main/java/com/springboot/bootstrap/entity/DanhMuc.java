package com.springboot.bootstrap.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "danh_muc")
@Getter
@Setter
@Builder
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_danh_muc")
    private String id;
    @Column(name = "ma")
    private String ma;
    @Column(name = "ten")
    private String ten;
    @Column(name = "trang_thai")
    private int trangThai;
}
