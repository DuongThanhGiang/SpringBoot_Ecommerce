package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface KhachHangService extends UserDetailsService {
    KhachHang getOne(String id);

    void save(KhachHang khachHang);

    List<KhachHang> getAll();
}
