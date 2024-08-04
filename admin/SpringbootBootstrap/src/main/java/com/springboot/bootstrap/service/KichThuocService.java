package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.KichThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KichThuocService {

    List<KichThuoc> findAllByTrangThai();


}
