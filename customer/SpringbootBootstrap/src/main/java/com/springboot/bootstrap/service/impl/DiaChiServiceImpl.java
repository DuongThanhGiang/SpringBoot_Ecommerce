package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.DiaChi;
import com.springboot.bootstrap.repository.DiaChiRepository;
import com.springboot.bootstrap.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    Timestamp currentTimestamp;

    @Override
    public Page<DiaChi> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 5);
        return diaChiRepository.findAll(pageable);
    }

    @Override
    public List<DiaChi> findAll() {
        return diaChiRepository.findAll();
    }

    @Override
    public DiaChi getOne(String id) {
        return diaChiRepository.findById(id).orElse(null);
    }

    @Override
    public void add(DiaChi diaChi) {
        java.util.Date currentDate = new java.util.Date();
        currentTimestamp = new Timestamp(currentDate.getTime());
        diaChi.setTaoLuc(currentTimestamp);
        diaChi.setTaoBoi("Giang");
        diaChiRepository.save(diaChi);
    }

    @Override
    public void update(DiaChi diaChi) {
        java.util.Date currentDate = new java.util.Date();
        currentTimestamp = new Timestamp(currentDate.getTime());
        diaChi.setSuaLuc(currentTimestamp);
        diaChi.setSuaBoi("Giang");
        diaChiRepository.save(diaChi);
    }

    @Override
    public void delete(String id) {
        DiaChi diaChi = diaChiRepository.findById(id).orElse(null);
        if (diaChi != null) {
            diaChi.setTrang_thai(diaChi.getTrang_thai() == 0 ? 1 : 0);
            diaChiRepository.save(diaChi);
        }
    }

    @Override
    public Page<DiaChi> searchDiaChi(String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 5);
        List list = diaChiRepository.searchDiaChi(keyword);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size()
                        ? list.size() : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<DiaChi>(list, pageable, diaChiRepository.searchDiaChi(keyword).size());
    }

//    @Override
//    public Page<DiaChi> searchTrangThai(int trang_thai, Integer pageNo) {
//        Pageable pageable = PageRequest.of(pageNo -1, 4);
//        return diaChiRepository.searchTrangThai(trang_thai, pageable);
//    }
}
