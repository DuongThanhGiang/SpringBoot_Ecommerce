package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonChiTiet;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.HoaDonChiTietRepository;
import com.springboot.bootstrap.repository.HoaDonRepository;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<HoaDonChiTiet> getList(UUID id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        List<HoaDonChiTiet> list = hoaDonChiTietRepository.findAllByHoaDon(hoaDon);
        return list;
    }

    @Override
    public void add(HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public void update(HoaDonChiTiet hoaDonChiTiet) {
        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public void delete(UUID idhdct) {
        hoaDonChiTietRepository.deleteById(idhdct);
    }

    @Override
    public HoaDonChiTiet getOne(UUID idhdct) {
        return hoaDonChiTietRepository.findById(idhdct).get();
    }

    @Override
    public Page<HoaDonChiTiet> getPage(UUID id, PageRequest pageable) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        List<HoaDonChiTiet> list = hoaDonChiTietRepository.findAllByHoaDon(hoaDon);
        Page<HoaDonChiTiet> page = new PageImpl(list,pageable,list.size());
        return page;
    }
}
