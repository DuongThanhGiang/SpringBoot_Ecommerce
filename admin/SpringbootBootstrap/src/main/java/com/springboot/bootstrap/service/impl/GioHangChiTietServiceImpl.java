package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.repository.GioHangChiTietRepository;
import com.springboot.bootstrap.service.GioHangChiTietService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;
    @Override
    public List<GioHangChiTiet> getListGhct(GioHang gioHang) {
        return gioHangChiTietRepository.findAllByGioHang(gioHang);
    }

    @Override
    public void deleteAllByGioHang(GioHang gioHang) {
        gioHangChiTietRepository.deleteAllByGioHang(gioHang);
    }
}
