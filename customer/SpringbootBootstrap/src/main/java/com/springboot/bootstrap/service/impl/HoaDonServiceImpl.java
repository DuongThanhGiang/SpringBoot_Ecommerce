package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.DTO.HoaDonDTO;
import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.NhanVien;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.repository.NhanVienRepo;
import com.springboot.bootstrap.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Override
    public List<HoaDon> getList() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Page<HoaDon> getListSearch(String keyword,Pageable pageable) {
        return hoaDonRepository.findAllByMaContaining(keyword,pageable);
    }

    @Override
    public Page<HoaDon> getAll(Pageable of) {
        return hoaDonRepository.findAllByOrderByTaoLucDesc(of);
    }

    @Override
    public HoaDon getOne(UUID id) {
        return hoaDonRepository.findById(id).get();
    }

    @Override
    public void save(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public List<HoaDon> renderTab() {
        return hoaDonRepository.findAllByTinhTrang(5);
    }

    @Override
    public void add(HoaDon hoaDon) {
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public void delete(UUID id) {
        hoaDonRepository.deleteById(id);
    }
}
