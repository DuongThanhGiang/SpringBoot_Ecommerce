package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface NhanVienService extends UserDetailsService {

    Page<NhanVien> getAll(Pageable pageable);

    NhanVien getOne(String idNV);

    List<NhanVien> findAll();

    void add(NhanVien nhanVien);

    void update(NhanVien nhanVien, String idNV);

    Page<NhanVien> searchTrangThai(int trangThai, Pageable pageable);

    Page<NhanVien> searchByEmail(String keyword,Pageable pageable);
}
