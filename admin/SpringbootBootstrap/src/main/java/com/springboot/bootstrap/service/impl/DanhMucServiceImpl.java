package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.DanhMuc;
import com.springboot.bootstrap.repository.DanhMucRepo;
import com.springboot.bootstrap.repository.MauSacRepo;
import com.springboot.bootstrap.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucRepo danhMucRepo;
    @Autowired
    private MauSacRepo mauSacRepo;




    @Override
    public List<DanhMuc> findAllByTrangThai() {
        return danhMucRepo.findAllByTrangThai(1);
    }


}
