package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepo extends JpaRepository<DanhMuc,String> {
    Page<DanhMuc> findAllByOrderByMaAsc(Pageable pageable);

    List<DanhMuc> findAllByTrangThai(int trangThai);

    @Query("SELECT dm FROM DanhMuc dm WHERE " +
            "LOWER(dm.ma) IS NULL OR   LOWER(dm.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(dm.ten) IS NULL OR  LOWER(dm.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<DanhMuc> searchCodeOrName(@Param("keyword") String keyword, Pageable pageable );

    @Query("SELECT dm FROM DanhMuc dm WHERE " +
            " (:trangThai IS NULL OR dm.trangThai = :trangThai) " )
    Page<DanhMuc> searchTrangThai(@Param("trangThai") int trangThai,Pageable pageable );
}
