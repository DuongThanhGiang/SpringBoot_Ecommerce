package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {
    HoaDon findByIdHoaDon(UUID uuid);
    List<HoaDon> findAllByKhachHangOrderByTaoLucDesc(KhachHang khachHang);
    List<HoaDon> findAllByKhachHangAndMaContaining(KhachHang khachHang,String keyword);

    @Query("SELECT hd FROM HoaDon hd " +
            "JOIN hd.khachHang kh "+
            "WHERE (:tinhTrang IS NULL OR hd.tinhTrang = :tinhTrang) " +
            "AND (:keyword IS NULL OR hd.ma LIKE LOWER(CONCAT('%', :keyword, '%')) ) " +
            "AND (:startDate IS NULL OR hd.taoLuc >= :startDate) " +
            "AND (:endDate IS NULL OR hd.taoLuc <= :endDate) " +
            "AND (:idKH IS NULL OR kh.idKhachHang LIKE LOWER(CONCAT('%', :idKH, '%'))) " +
            "ORDER BY hd.taoLuc DESC ")
    List<HoaDon> filterHoaDon(Integer tinhTrang, String keyword, LocalDateTime startDate, LocalDateTime endDate,String idKH, Pageable pageable);
}


