package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.KhachHang;

import java.util.List;
import java.util.UUID;

public interface HoaDonService {
    void add(HoaDon hoaDon);

    HoaDon getOne(UUID fromString);

    List<HoaDon> getListHoaDon(KhachHang khachHang);

    List<HoaDon> getListSearch(String keyword, KhachHang khachHang);

    void save(HoaDon hoaDon);
}
