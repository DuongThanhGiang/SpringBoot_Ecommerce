package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.DTO.HoaDonDTO;
import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface HoaDonService {
    void delete(UUID id);

    void add(HoaDon hoaDon);

    List<HoaDon> renderTab();

    List<HoaDon> getList();

    Page<HoaDon> getListSearch(String keyword,Pageable pageable);

    Page<HoaDon> getAll(Pageable of);


    HoaDon getOne(UUID fromString);

    void save(HoaDon hoaDon);
}
