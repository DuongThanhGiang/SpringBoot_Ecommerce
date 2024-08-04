package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.NhanVien;
import com.springboot.bootstrap.entity.SanPhamCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {
    List<HoaDon> findAllByTinhTrang(Integer tinhTrang);

    Page<HoaDon> findAllByOrderByTaoLucDesc(Pageable pageable);

    Page<HoaDon> findAllByMaContaining(String keyword, Pageable pageable);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE YEAR(h.taoLuc) = YEAR(:taoLuc) AND MONTH(h.taoLuc) = MONTH(:taoLuc) AND DAY(h.taoLuc) = DAY(:taoLuc) AND (h.tinhTrang = 2 OR h.tinhTrang = 3 OR h.tinhTrang = 4)")
    Integer soDonHangTheoNgayTao(Date taoLuc);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE YEAR(h.taoLuc) = YEAR(:taoLuc) AND MONTH(h.taoLuc) = MONTH(:taoLuc) AND (h.tinhTrang = 2 OR h.tinhTrang = 3 OR h.tinhTrang = 4)")
    Integer soDonHangTheoThangVaNam(Date taoLuc);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE YEAR(h.taoLuc) = YEAR(:taoLuc) AND (h.tinhTrang = 2 OR h.tinhTrang = 3 OR h.tinhTrang = 4)")
    Integer soDonHangTheoNam(Date taoLuc);


    @Query("SELECT COALESCE(SUM(h.thanhTien), 0) FROM HoaDon h WHERE YEAR(h.taoLuc) = YEAR(:taoLuc) AND MONTH(h.taoLuc) = MONTH(:taoLuc) AND DAY(h.taoLuc) = DAY(:taoLuc) AND h.tinhTrang = 4")
    Integer doanhThuTheoNgayTao(Date taoLuc);

    @Query("SELECT COALESCE(SUM(h.thanhTien), 0) FROM HoaDon h WHERE YEAR(h.taoLuc) = YEAR(:taoLuc) AND MONTH(h.taoLuc) = MONTH(:taoLuc) AND h.tinhTrang = 4")
    Integer doanhThuTheoThangVaNam(Date taoLuc);

    @Query("SELECT COALESCE(SUM(h.thanhTien), 0) FROM HoaDon h WHERE YEAR(h.taoLuc) = YEAR(:taoLuc) AND h.tinhTrang = 4")
    Integer doanhThuTheoNam(Date taoLuc);

    //filter
    @Query("SELECT hd FROM HoaDon hd " +
            "WHERE (:tinhTrang IS NULL OR hd.tinhTrang = :tinhTrang) " +
            "AND (:hinhThuc IS NULL OR hd.hinhThuc = :hinhThuc) " +
            "AND (:keyword IS NULL OR hd.ma LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
            "AND (:startDate IS NULL OR hd.taoLuc >= :startDate) " +
            "AND (:endDate IS NULL OR hd.taoLuc <= :endDate) " +
            "ORDER BY hd.taoLuc DESC ")
    Page<HoaDon> filterHoaDon(Integer tinhTrang, Integer hinhThuc, String keyword, LocalDateTime startDate, LocalDateTime endDate,Pageable pageable);
}
