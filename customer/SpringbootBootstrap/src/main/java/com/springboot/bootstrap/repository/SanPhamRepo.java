package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham,String> {
    Page<SanPham> findAllByOrderByTaoLucDesc(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp  WHERE sp.trangThai = 1 ORDER BY sp.ma")
    Page<SanPham> getAllByTrangThai(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.thuongHieu th " +
            "WHERE (:danhMucId IS NULL OR dm.id LIKE LOWER(CONCAT('%', :danhMucId, '%'))) " +
            "AND (:thuongHieuId IS NULL OR th.id LIKE LOWER(CONCAT('%', :thuongHieuId, '%'))) " +
            "AND (:keyword IS NULL OR sp.ma LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            " AND (:trangThai IS NULL OR sp.trangThai =:trangThai)")
    Page<SanPham> filter(String danhMucId, String thuongHieuId, Integer trangThai,String keyword, Pageable pageable);



}
