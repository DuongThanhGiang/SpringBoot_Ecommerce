package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.SanPhamCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanPhamCTService {
    void add(SanPhamCT sanPhamCT);

    SanPhamCT getOne(String id);

    List<SanPhamCT> findAllBySP(String idSP);

    void save(SanPhamCT sanPhamCT);
}
