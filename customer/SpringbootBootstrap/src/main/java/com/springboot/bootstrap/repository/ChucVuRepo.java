package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChucVuRepo extends JpaRepository<ChucVu, String> {

    Page<ChucVu> findAll(Pageable pageable);

    @Query("SELECT cv FROM ChucVu cv WHERE " +
            "LOWER(cv.ten) IS NULL OR  LOWER(cv.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ChucVu> searchCodeOrName(@Param("keyword") String keyword, Pageable pageable );

    @Query("SELECT cv FROM MauSac cv WHERE " +
            " (:trangThai IS NULL OR cv.trangThai = :trangThai) " )
    Page<ChucVu> searchTrangThai(@Param("trangThai") int trangThai,Pageable pageable );

    ChucVu findByTen(String ten);

}
