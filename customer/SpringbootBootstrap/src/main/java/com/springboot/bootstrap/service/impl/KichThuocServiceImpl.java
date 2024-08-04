package com.springboot.bootstrap.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.repository.KichThuocRepo;
import com.springboot.bootstrap.service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class KichThuocServiceImpl implements KichThuocService {
    @Autowired
    private KichThuocRepo kichThuocRepo;
    private static final String ma = "KT";
    private static int counter = 0;

    @Override
    public List<KichThuoc> findAllByTrangThai() {
        return kichThuocRepo.findAllByTrangThai(1);
    }

    @Override
    public KichThuoc getOne(String id) {
        return kichThuocRepo.findById(id).orElse(null);
    }

    @Override
    public Page<KichThuoc> getAll(Pageable pageable) {
        return kichThuocRepo.findAllByOrderByMaAsc(pageable);
    }

    @Override
    public void add(KichThuoc kichThuoc) {
        kichThuocRepo.save(kichThuoc);
    }

    @Override
    public void update(KichThuoc kichThuoc, String id) {
        kichThuoc.setId(id);
        kichThuocRepo.save(kichThuoc);
    }

    @Override
    public Page<KichThuoc> searchCodeOrName(String keyword, Pageable pageable) {
        return kichThuocRepo.searchCodeOrName(keyword, pageable);
    }

    @Override
    public Page<KichThuoc> searchTrangThai(int trangThai, Pageable pageable) {
        return kichThuocRepo.searchTrangThai(trangThai, pageable);
    }


    @Override
    public String generateMaKT() {
        counter++;
        return ma + String.format("%03d", counter);
    }
}

