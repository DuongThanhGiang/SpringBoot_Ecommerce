package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;

import java.util.List;

public interface GioHangChiTietService {
    List<GioHangChiTiet> getListGhct(GioHang gioHang);

    void deleteAllByGioHang(GioHang idByIdKh);
}
