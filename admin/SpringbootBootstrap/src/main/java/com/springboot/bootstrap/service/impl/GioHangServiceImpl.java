package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.repository.GioHangRepository;
import com.springboot.bootstrap.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;
    @Override
    public GioHang getIdByIdKh(KhachHang khachHang) {
        return gioHangRepository.findByKhachHang(khachHang);
    }

    @Override
    public void update(GioHang gioHang) {
        gioHangRepository.save(gioHang);
    }
}
