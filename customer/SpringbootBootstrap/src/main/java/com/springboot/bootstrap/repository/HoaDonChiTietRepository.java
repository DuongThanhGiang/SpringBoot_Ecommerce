package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import java.sql.Date;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    List<HoaDonChiTiet> findAllByHoaDon(HoaDon hoaDon);

    @Query("SELECT SUM(hdct.soLuong) FROM HoaDonChiTiet hdct WHERE hdct.hoaDon.ngayThanhToan BETWEEN :tuNgay AND :denNgay")
    Integer soSanPhamDaBanTheoKhoangThoiGian(@Param("tuNgay") Date tuNgay, @Param("denNgay") Date denNgay);

    @Query("SELECT SUM(hdct.soLuong) FROM HoaDonChiTiet hdct WHERE YEAR(hdct.hoaDon.ngayThanhToan) = YEAR(:ngayThanhToan) AND MONTH(hdct.hoaDon.ngayThanhToan) = MONTH(:ngayThanhToan) AND DAY(hdct.hoaDon.ngayThanhToan) = DAY(:ngayThanhToan) AND hdct.hoaDon.tinhTrang = 4")
    Integer soSanPhamDaBanTheoNgay(Date ngayThanhToan);

    @Query("SELECT COALESCE(SUM(hdct.soLuong), 0) FROM HoaDonChiTiet hdct WHERE YEAR(hdct.hoaDon.ngayThanhToan) = YEAR(:ngayThanhToan) AND MONTH(hdct.hoaDon.ngayThanhToan) = MONTH(:ngayThanhToan) AND hdct.hoaDon.tinhTrang = 4")
    Integer soSanPhamDaBanTheoThangVaNam(Date ngayThanhToan);

    @Query("SELECT COALESCE(SUM(hdct.soLuong), 0) FROM HoaDonChiTiet hdct WHERE YEAR(hdct.hoaDon.ngayThanhToan) = YEAR(:ngayThanhToan) AND hdct.hoaDon.tinhTrang = 4")
    Integer soSanPhamDaBanTheoNam(Date ngayThanhToan);

}
