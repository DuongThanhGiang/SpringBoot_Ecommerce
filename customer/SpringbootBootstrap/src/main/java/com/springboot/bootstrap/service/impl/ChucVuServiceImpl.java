package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.ChucVu;
import com.springboot.bootstrap.repository.ChucVuRepo;
import com.springboot.bootstrap.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    private ChucVuRepo chucVuRepo;
    private static final String ma = "CV";
    private static int counter = 0;

    Timestamp currentTimestamp;

    @Override
    public Page<ChucVu> getAll(Pageable pageable) {

        return chucVuRepo.findAll(pageable);
    }

    @Override
    public ChucVu getOne(String idCV) {
        ChucVu c = chucVuRepo.findById(idCV).orElse(null);
        return c;
    }

    @Override
    public void add(ChucVu chucVu) {
        chucVuRepo.save(chucVu);
    }

    @Override
    public void update(ChucVu chucVu, String idCV) {
        chucVu.setIdCV(idCV);
        chucVuRepo.save(chucVu);
    }

    @Override
    public Page<ChucVu> searchCodeOrName(String keyword, Pageable pageable) {
        return chucVuRepo.searchCodeOrName(keyword, pageable);
    }

    @Override
    public Page<ChucVu> searchTrangThai(int trangThai, Pageable pageable) {
        return chucVuRepo.searchTrangThai(trangThai, pageable);
    }

    @Override
    public List<ChucVu> findAll() {
        return chucVuRepo.findAll();
    }

    @Override
    public ChucVu getByRole(String role_staff) {
        return chucVuRepo.findByTen(role_staff);
    }

}
