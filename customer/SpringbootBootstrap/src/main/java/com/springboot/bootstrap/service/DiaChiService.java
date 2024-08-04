package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.DiaChi;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiaChiService {

    Page<DiaChi> getAll(Integer pageNo);

    List<DiaChi> findAll();

    DiaChi getOne(String id);

    void add(DiaChi diaChi);

    void update(DiaChi diaChi);

    void delete(String id);

    Page<DiaChi> searchDiaChi( String keyword, Integer pageNo);

//    Page<DiaChi> searchTrangThai( int trang_thai,Integer pageNo );
}
