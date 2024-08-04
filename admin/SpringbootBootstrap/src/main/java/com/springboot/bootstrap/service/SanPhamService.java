package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanPhamService {

    //tại quầy


    SanPham detail(String id);


    //online
    Page<SanPham> getAllByTT(Pageable pageable);
}
