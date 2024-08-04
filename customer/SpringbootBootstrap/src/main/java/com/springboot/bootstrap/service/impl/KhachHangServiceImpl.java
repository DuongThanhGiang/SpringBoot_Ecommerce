package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.repository.KhachHangRepository;
import com.springboot.bootstrap.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    Timestamp currentTimestamp;

    @Override
    public Page<KhachHang> getAll(Pageable pageable) {
        return khachHangRepository.findAll(pageable);
    }

    @Override
    public KhachHang getOne(String id) {
        return khachHangRepository.findById(id).orElse(null);
    }

    @Override
    public void add(KhachHang khachHang) {
        khachHangRepository.save(khachHang);
    }

    @Override
    public void update(KhachHang khachHang) {
        khachHangRepository.save(khachHang);
    }

    @Override
    public void delete(String id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
        if (khachHang != null) {
            khachHang.setTrangThai(khachHang.getTrangThai() == 0 ? 1 : 0);
            khachHangRepository.save(khachHang);
        }
    }

    @Override
    public Page<KhachHang> searchCodeOrName(String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 4);
        List list = khachHangRepository.searchCodeOrName(keyword);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size()
                ? list.size() : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<KhachHang>(list, pageable, khachHangRepository.searchCodeOrName(keyword).size());
    }

    @Override
    public List<KhachHang> findAll() {
        return khachHangRepository.findAll();
    }

    @Override
    public Page<KhachHang> searchByEmail(String keyword,Pageable pageable) {
        return khachHangRepository.findAllByEmailContaining(keyword,pageable);
    }

}
