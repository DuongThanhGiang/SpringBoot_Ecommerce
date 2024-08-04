package com.springboot.bootstrap.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Anh")
@Getter
@Setter
@Builder
public class Anh {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_hinh_anh")
    private String id;

    @Lob
    @Column(name = "duong_dan")
    private byte[] data;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id_san_pham")
    private SanPham sanPham;
}
