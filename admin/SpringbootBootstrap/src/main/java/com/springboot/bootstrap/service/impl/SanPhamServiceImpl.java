package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.SanPham;
import com.springboot.bootstrap.repository.SanPhamRepo;
import com.springboot.bootstrap.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepo sanPhamRepo;


    @Override
    public SanPham detail(String id) {
        return sanPhamRepo.findById(id).get();
    }

//online

    @Override
    public Page<SanPham> getAllByTT(Pageable pageable) {
        return sanPhamRepo.getAllByTrangThai(pageable);
    }

}
