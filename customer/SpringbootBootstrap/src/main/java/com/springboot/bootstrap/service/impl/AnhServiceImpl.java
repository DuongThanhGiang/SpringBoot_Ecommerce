package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.service.AnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnhServiceImpl implements AnhService {
    @Autowired
    private AnhRepo anhRepo;

    @Override
    public void add(Anh anh) {
        anhRepo.save(anh);
    }

    @Override
    public Anh delete(String idImg) {
        Optional<Anh> optional=anhRepo.findById(idImg);
        return optional.map(o->{
            anhRepo.delete(o);
            return o;
        }).orElse(null);
    }
}
