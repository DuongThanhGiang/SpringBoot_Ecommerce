package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.KichThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KichThuocRepo extends JpaRepository<KichThuoc,String> {
    Page<KichThuoc> findAllByOrderByMaAsc(Pageable pageable);
    List<KichThuoc> findAllByTrangThai(int trangThai);
    @Query("SELECT kt FROM KichThuoc kt WHERE " +
            "LOWER(kt.ma) IS NULL OR   LOWER(kt.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(kt.ten) IS NULL OR  LOWER(kt.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<KichThuoc> searchCodeOrName(@Param("keyword") String keyword, Pageable pageable );

    @Query("SELECT kt FROM KichThuoc kt WHERE " +
            " (:trangThai IS NULL OR kt.trangThai = :trangThai) " )
    Page<KichThuoc> searchTrangThai(@Param("trangThai") int trangThai,Pageable pageable );
}
