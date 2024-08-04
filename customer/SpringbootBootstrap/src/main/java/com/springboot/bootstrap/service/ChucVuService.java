package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChucVuService {

    Page<ChucVu> getAll(Pageable pageable);

    ChucVu getOne(String idCV);

    void add(ChucVu chucVu);

    void update(ChucVu chucVu, String idCV);

    Page<ChucVu> searchCodeOrName(String keyword, Pageable pageable);

    Page<ChucVu> searchTrangThai(int trangThai, Pageable pageable);


    List<ChucVu> findAll();

    ChucVu getByRole(String role_staff);
}
