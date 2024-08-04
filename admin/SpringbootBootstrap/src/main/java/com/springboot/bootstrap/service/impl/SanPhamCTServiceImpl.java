package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.SanPhamCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamCTServiceImpl implements SanPhamCTService {
    @Override
    public void add(SanPhamCT sanPhamCT) {
        sanPhamCTRepo.save(sanPhamCT);
    }

    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;

    @Override
    public SanPhamCT getOne(String id) {
        return sanPhamCTRepo.findById(id).get();
    }

    @Override
    public List<SanPhamCT> findAllBySP(String idSP) {
        return sanPhamCTRepo.findAllBySP(idSP);
    }

    @Override
    public void save(SanPhamCT sanPhamCT) {
        sanPhamCTRepo.save(sanPhamCT);
    }
}
