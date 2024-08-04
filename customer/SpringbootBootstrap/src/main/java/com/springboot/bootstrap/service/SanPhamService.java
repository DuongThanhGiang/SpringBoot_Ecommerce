package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanPhamService {

    //tại quầy
    void add(SanPham sanPham);

    void update(String id,SanPham sanPham);

    SanPham detail(String id);

    Page<SanPham> getAll(Pageable pageable);

}
