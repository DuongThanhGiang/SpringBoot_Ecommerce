package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.entity.SanPhamCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamCTRepo extends JpaRepository<SanPhamCT, String> {


    //onl
    @Query("SELECT spct FROM SanPhamCT spct JOIN spct.sanPham sp  WHERE sp.id = :sanPhamId  ")
    List<SanPhamCT> findAllBySP(String sanPhamId);

    @Query("SELECT MAX(spct.gia) FROM SanPhamCT spct JOIN spct.sanPham sp  WHERE sp.id = :sanPhamId  ")
    String giaMax (String sanPhamId);

    @Query("SELECT MIN(spct.gia) FROM SanPhamCT spct JOIN spct.sanPham sp  WHERE sp.id = :sanPhamId  ")
    String giaMin (String sanPhamId);

    @Query("SELECT DISTINCT spct.mauSac FROM SanPhamCT spct JOIN spct.sanPham sp  WHERE sp.id = :sanPhamId  ")
    List<MauSac> mauSP(String sanPhamId);

    @Query("SELECT DISTINCT spct.kichThuoc FROM SanPhamCT spct JOIN spct.sanPham sp  WHERE sp.id = :sanPhamId  ")
    List<KichThuoc> sizeSP(String sanPhamId);

    @Query("SELECT  spct FROM SanPhamCT spct JOIN spct.sanPham sp JOIN spct.mauSac ms  JOIN spct.kichThuoc kt  WHERE sp.id = :sanPhamId AND ms.id=:idMS")
    List<SanPhamCT> findKTByMS(String sanPhamId,String idMS);

    @Query("SELECT  spct FROM SanPhamCT spct JOIN spct.sanPham sp JOIN spct.mauSac ms  JOIN spct.kichThuoc kt  WHERE sp.id = :sanPhamId AND ms.id=:idMS AND kt.id=:idKT")
    SanPhamCT findSPCTByKTAndMS(String sanPhamId,String idMS,String idKT);
}
