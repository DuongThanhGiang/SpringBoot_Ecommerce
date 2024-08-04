package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.repository.MauSacRepo;
import com.springboot.bootstrap.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacServiceImpl implements MauSacService {
    @Autowired
    private MauSacRepo mauSacRepo;
    private static final String ma = "MS";
    private static int counter = 0;

    @Override
    public List<MauSac> findAllByTrangThai() {
        return mauSacRepo.findAllByTrangThai(1);
    }

    @Override
    public Page<MauSac> getAll(Pageable pageable) {
        return mauSacRepo.findAllByOrderByMaAsc(pageable);


    }

    @Override
    public MauSac getOne(String id) {
        return mauSacRepo.findById(id).orElse(null);
    }

    @Override
    public void add(MauSac mauSac) {
        mauSacRepo.save(mauSac);
    }

    @Override
    public void update(MauSac mauSac, String id) {
        mauSac.setId(id);
        mauSacRepo.save(mauSac);
    }

    @Override
    public Page<MauSac> searchCodeOrName(String keyword, Pageable pageable) {
        return mauSacRepo.searchCodeOrName(keyword, pageable);
    }

    @Override
    public Page<MauSac> searchTrangThai(int trangThai, Pageable pageable) {
        return mauSacRepo.searchTrangThai(trangThai, pageable);
    }

    @Override
    public String generateMaMS() {
        counter++;
        return ma + String.format("%03d", counter);
    }
}
