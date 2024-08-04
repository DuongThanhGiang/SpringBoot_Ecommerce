package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.KhachHang;

public interface GioHangService {
    GioHang getIdByIdKh(KhachHang khachHang);

    void update(GioHang gioHang);
}
