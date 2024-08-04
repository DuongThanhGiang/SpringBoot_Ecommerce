package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham,String> {
    Page<SanPham> findAllByOrderByMaAsc(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp  WHERE sp.trangThai = 1 ORDER BY sp.ma")
    Page<SanPham> getAllByTrangThai(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp WHERE sp.danhMuc.id IN :idDM")
    Page<SanPham> findByTenDMs(List<String> idDM ,Pageable pageable);

    @Query("SELECT sp FROM SanPham sp WHERE sp.thuongHieu.id IN :idTH")
    Page<SanPham> findByTenTHs(List<String> idTH,Pageable pageable);

    @Query("SELECT spct.sanPham FROM SanPhamCT spct WHERE spct.kichThuoc.id IN :idKT")
    Page<SanPham> findByTenKTs(List<String> idKT,Pageable pageable);

    @Query("SELECT spct.sanPham FROM SanPhamCT spct WHERE spct.mauSac.id IN :idMS")
    Page<SanPham> findByTenMSs(List<String> idMS,Pageable pageable);

    @Query("SELECT sp ,SUM(hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "JOIN hdct.sanPhamChiTiet spct " +
            "JOIN hdct.sanPhamChiTiet.sanPham sp " +
            "GROUP BY sp " +
            "ORDER BY COUNT(hdct.soLuong) DESC")
    List<SanPham> findSPBanChay();
    default List<SanPham> findTop6SanPhamBanChay() {
        List<SanPham> allSPBC = findSPBanChay();
        return allSPBC.subList(0, Math.min(6, allSPBC.size()));
    }

    @Query("SELECT sp FROM SanPham sp where sp.trangThai=1 ORDER BY sp.taoLuc DESC")
    List<SanPham> findAllSPByTaoLuc();
    default List<SanPham> findTop6SanPhamMoiNhat() {
        List<SanPham> allSPNEW = findAllSPByTaoLuc();
        return allSPNEW.subList(0, Math.min(6, allSPNEW.size()));
    }


}
