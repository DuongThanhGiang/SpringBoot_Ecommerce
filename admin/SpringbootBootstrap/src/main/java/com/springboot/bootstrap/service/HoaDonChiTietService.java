package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {
    public void add(HoaDonChiTiet hoaDonChiTiet);

    List<HoaDonChiTiet> getListHoaDonChiTiet(HoaDon hoaDon);
}
