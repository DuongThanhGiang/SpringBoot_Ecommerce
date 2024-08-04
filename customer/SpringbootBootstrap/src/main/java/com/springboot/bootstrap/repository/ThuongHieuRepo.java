package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepo extends JpaRepository<ThuongHieu, String> {
    Page<ThuongHieu> findAllByOrderByMaAsc(Pageable pageable);

    List<ThuongHieu> findAllByTrangThai(int trangThai);

    @Query("SELECT th FROM ThuongHieu th WHERE " +
            "LOWER(th.ma) IS NULL OR   LOWER(th.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(th.ten) IS NULL OR  LOWER(th.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ThuongHieu> searchCodeOrName(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT th FROM ThuongHieu th WHERE " +
            " (:trangThai IS NULL OR th.trangThai = :trangThai) ")
    Page<ThuongHieu> searchTrangThai(@Param("trangThai") int trangThai, Pageable pageable);
}
