package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MauSacService {


    List<MauSac> findAllByTrangThai();


}
