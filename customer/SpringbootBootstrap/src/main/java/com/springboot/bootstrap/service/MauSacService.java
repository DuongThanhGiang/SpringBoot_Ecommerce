package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MauSacService {
    public String generateMaMS();

    Page<MauSac> getAll(Pageable pageable);

    MauSac getOne(String id);

    List<MauSac> findAllByTrangThai();

    void add(MauSac mauSac);

    void update(MauSac mauSac, String id);

    Page<MauSac> searchCodeOrName(String keyword, Pageable pageable);

    Page<MauSac> searchTrangThai(int trangThai, Pageable pageable);
}
