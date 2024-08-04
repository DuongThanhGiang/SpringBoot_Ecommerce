package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.repository.HoaDonChiTietRepository;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public void add(HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public List<HoaDonChiTiet> getListHoaDonChiTiet(HoaDon hoaDon) {
        return hoaDonChiTietRepository.findAllByHoaDon(hoaDon);
    }
}

