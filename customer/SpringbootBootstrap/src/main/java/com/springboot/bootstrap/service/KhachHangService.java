package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhachHangService {

    Page<KhachHang> getAll(Pageable pageable);

    KhachHang getOne(String id);

    void add(KhachHang khachHang);

    void update(KhachHang khachHang);

    void delete(String id);

    Page<KhachHang> searchCodeOrName(String keyword, Integer pageNo );

    List<KhachHang> findAll();

    Page<KhachHang> searchByEmail(String keyword,Pageable pageable);
}
