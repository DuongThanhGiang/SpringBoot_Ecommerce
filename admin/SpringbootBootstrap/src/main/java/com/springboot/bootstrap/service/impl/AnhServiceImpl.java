package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.service.AnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnhServiceImpl implements AnhService {
    @Autowired
    private AnhRepo anhRepo;

    @Override
    public void add(Anh anh) {
        anhRepo.save(anh);
    }
}
