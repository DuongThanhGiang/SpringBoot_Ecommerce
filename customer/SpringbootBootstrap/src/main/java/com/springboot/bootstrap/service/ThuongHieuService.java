package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThuongHieuService {
    Page<ThuongHieu> getAll(Pageable pageable);

    List<ThuongHieu> findAllByTrangThai();

    public String generateMaTH();

    ThuongHieu getOne(String id);

    void add(ThuongHieu thuongHieu);

    void update(ThuongHieu thuongHieu, String id);

    Page<ThuongHieu> searchCodeOrName(String keyword, Pageable pageable);

    Page<ThuongHieu> searchTrangThai(int trangThai, Pageable pageable);
}
