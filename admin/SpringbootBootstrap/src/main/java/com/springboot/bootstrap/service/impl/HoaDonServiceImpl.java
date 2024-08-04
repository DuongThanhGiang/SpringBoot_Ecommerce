package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public HoaDon getOne(UUID id) {
        return hoaDonRepository.findByIdHoaDon(id);
    }

    @Override
    public List<HoaDon> getListHoaDon(KhachHang khachHang) {
        List<HoaDon> list = hoaDonRepository.findAllByKhachHangOrderByTaoLucDesc(khachHang);
        return list;
    }

    @Override
    public List<HoaDon> getListSearch(String keyword, KhachHang khachHang) {
        return hoaDonRepository.findAllByKhachHangAndMaContaining(khachHang,keyword);
    }

    @Override
    public void save(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public void add(HoaDon hoaDon) {
        hoaDonRepository.saveAndFlush(hoaDon);
    }






}
