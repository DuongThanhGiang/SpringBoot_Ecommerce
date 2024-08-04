package com.springboot.bootstrap.repository;


import com.springboot.bootstrap.entity.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, UUID> {
//    List<PhieuGiamGia> findByNgayBatDauBetweenAndNgayKetThucBetween(Date x, Date y);
//    List<PhieuGiamGia> findByTrangThai(int x);
//    List<PhieuGiamGia> findByMaOrTenContaining(String x);

    PhieuGiamGia findByMa(String ma);

    Page<PhieuGiamGia> findAll(Pageable pageable);

    PhieuGiamGia findTop1ByTrangThaiAndGiaTriToiThieuGreaterThanEqualAndGiaTriGiamToiDaLessThanEqual(int trangThai, double giaTriToiThieu, double giaTriGiamToiDa);

    @Query("SELECT pgg FROM PhieuGiamGia pgg " +
            "WHERE pgg.trangThai = 1 " +
            "AND pgg.giaTriToiThieu <= :gia " +
            "AND (" +
            "   (pgg.donVi = 1)"+
            "   OR " +
            "   (pgg.donVi = 2 AND pgg.giaTriGiamToiDa >= pgg.giaTriGiam)" +
            ") " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN pgg.donVi = 1 THEN :gia * (pgg.giaTriGiam/100)" +
            "       ELSE pgg.giaTriGiam " +
            "   END DESC")
    List<PhieuGiamGia> findTopPhieuGiamGia(@Param("gia") double gia, Pageable pageable);


    List<PhieuGiamGia> findAllByTrangThai(int trangThai);

    @Query("SELECT ms FROM PhieuGiamGia ms WHERE " +
            "LOWER(ms.ma) IS NULL OR   LOWER(ms.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(ms.ten) IS NULL OR  LOWER(ms.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<PhieuGiamGia> searchCodeOrName(String keyword, Pageable pageable );

    @Query("SELECT ms FROM PhieuGiamGia ms WHERE " +
            " (:trangThai IS NULL OR ms.trangThai = :trangThai) " )
    Page<PhieuGiamGia> searchTrangThai(Integer trangThai,Pageable pageable);

    @Query("SELECT ms FROM PhieuGiamGia ms WHERE " +
            "(LOWER(ms.ma) IS NULL OR   LOWER(ms.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(ms.ten) IS NULL OR  LOWER(ms.ten) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND" +
            " (:trangThai IS NULL OR ms.trangThai = :trangThai) ")
    Page<PhieuGiamGia> searchCodeOrNameAndTrangThai(String keyword, Integer trangThai, Pageable pageable);

}
