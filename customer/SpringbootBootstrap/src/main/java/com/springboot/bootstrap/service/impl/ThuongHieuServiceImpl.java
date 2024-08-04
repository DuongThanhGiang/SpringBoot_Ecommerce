package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.ThuongHieu;
import com.springboot.bootstrap.repository.ThuongHieuRepo;
import com.springboot.bootstrap.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {
    @Autowired
    private ThuongHieuRepo thuongHieuRepo;
    private static final String ma = "TH";
    private static int counter = 0;

    @Override
    public Page<ThuongHieu> getAll(Pageable pageable) {
        return thuongHieuRepo.findAllByOrderByMaAsc(pageable);
    }

    @Override
    public ThuongHieu getOne(String id) {
        return thuongHieuRepo.findById(id).orElse(null);
    }

    @Override
    public void add(ThuongHieu thuongHieu) {
        thuongHieuRepo.save(thuongHieu);
    }

    @Override
    public void update(ThuongHieu thuongHieu, String id) {
        thuongHieu.setId(id);
        thuongHieuRepo.save(thuongHieu);
    }

    @Override
    public Page<ThuongHieu> searchCodeOrName(String keyword, Pageable pageable) {
        return thuongHieuRepo.searchCodeOrName(keyword, pageable);
    }

    @Override
    public Page<ThuongHieu> searchTrangThai(int trangThai, Pageable pageable) {
        return thuongHieuRepo.searchTrangThai(trangThai, pageable);
    }

    @Override
    public List<ThuongHieu> findAllByTrangThai() {
        return thuongHieuRepo.findAllByTrangThai(1);
    }

    @Override
    public String generateMaTH() {
        counter++;
        return ma + String.format("%03d", counter);
    }
}
