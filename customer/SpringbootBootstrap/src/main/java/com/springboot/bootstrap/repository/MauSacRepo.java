package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepo extends JpaRepository<MauSac,String> {
    Page<MauSac> findAllByOrderByMaAsc(Pageable pageable);
    List<MauSac> findAllByTrangThai(int trangThai);

    @Query("SELECT ms FROM MauSac ms WHERE " +
            "LOWER(ms.ma) IS NULL OR   LOWER(ms.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(ms.ten) IS NULL OR  LOWER(ms.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<MauSac> searchCodeOrName(@Param("keyword") String keyword, Pageable pageable );

    @Query("SELECT ms FROM MauSac ms WHERE " +
            " (:trangThai IS NULL OR ms.trangThai = :trangThai) " )
    Page<MauSac> searchTrangThai(@Param("trangThai") int trangThai,Pageable pageable );




}
