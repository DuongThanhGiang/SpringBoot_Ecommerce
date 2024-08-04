package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThuongHieuService {

    List<ThuongHieu> findAllByTrangThai();


}
