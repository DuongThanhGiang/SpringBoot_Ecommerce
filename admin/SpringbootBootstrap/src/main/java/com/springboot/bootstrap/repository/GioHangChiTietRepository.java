package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {
    List<GioHangChiTiet> findAllByGioHang(GioHang gioHang);

    void deleteAllByGioHang(GioHang gioHang);

    @Query("select ghct from GioHangChiTiet  ghct JOIN ghct.gioHang gh JOIN ghct.gioHang.khachHang kh where kh=:khachHang")
    List<GioHangChiTiet> getAllByKhachHang(KhachHang khachHang);

    @Query("select ghct from GioHangChiTiet  ghct JOIN ghct.gioHang gh JOIN ghct.gioHang.khachHang kh JOIN ghct.sanPhamCT spct where spct.id=:idSPCT and kh=:khachHang ")
    GioHangChiTiet getBySPCT(String idSPCT, KhachHang khachHang);

    @Query("select ghct from GioHangChiTiet  ghct JOIN ghct.gioHang gh JOIN ghct.gioHang.khachHang kh join ghct.sanPhamCT.sanPham where kh=:khachHang")
    Page<GioHangChiTiet> findAllByKH(KhachHang khachHang, Pageable pageable);
}
