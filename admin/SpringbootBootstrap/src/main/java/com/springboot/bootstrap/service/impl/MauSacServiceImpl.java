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


    @Override
    public List<MauSac> findAllByTrangThai() {
        return mauSacRepo.findAllByTrangThai(1);
    }


}
