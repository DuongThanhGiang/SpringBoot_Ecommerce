package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

    List<KhachHang> findAllByTrangThai(int trangThai);

    Page<KhachHang> findAll(Pageable pageable);

    @Query("SELECT kh FROM KhachHang kh WHERE " +
            "LOWER(kh.ten) IS NULL OR  LOWER(kh.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<KhachHang> searchCodeOrName(@Param("keyword") String keyword);

    @Query("SELECT dc FROM KhachHang dc WHERE " +
            " (:trangThai IS NULL OR dc.trangThai = :trangThai) " )
    Page<KhachHang> searchTrangThai(@Param("trangThai") int trangThai, Pageable pageable);

    Page<KhachHang> findAllByEmailContaining(String keyword, Pageable pageable);
}
