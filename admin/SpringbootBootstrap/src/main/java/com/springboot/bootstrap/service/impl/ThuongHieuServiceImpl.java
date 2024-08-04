package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.ThuongHieu;
import com.springboot.bootstrap.repository.ThuongHieuRepo;
import com.springboot.bootstrap.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {
    @Autowired
    private ThuongHieuRepo thuongHieuRepo;










    @Override
    public List<ThuongHieu> findAllByTrangThai() {
        return thuongHieuRepo.findAllByTrangThai(1);
    }


}
