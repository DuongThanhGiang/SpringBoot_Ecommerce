package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.DanhMuc;
import com.springboot.bootstrap.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DanhMucService {
    public String generateMaDM();

    List<DanhMuc> findAllByTrangThai();

    Page<DanhMuc> getAll(Pageable pageable);

    DanhMuc getOne(String id);

    void add(DanhMuc danhMuc);

    void update(DanhMuc danhMuc, String id);

    Page<DanhMuc> searchCodeOrName(String keyword, Pageable pageable);

    Page<DanhMuc> searchTrangThai(int trangThai, Pageable pageable);
}
