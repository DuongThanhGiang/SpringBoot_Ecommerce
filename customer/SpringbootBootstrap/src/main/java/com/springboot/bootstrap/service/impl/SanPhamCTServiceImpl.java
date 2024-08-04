package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.SanPhamCTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamCTServiceImpl implements SanPhamCTService {
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;


    @Override
    public Page<SanPhamCT> getBySL(Pageable pageable) {
        return sanPhamCTRepo.findBySL(pageable);
    }

    @Override
    public SanPhamCT getOne(String id) {
        return sanPhamCTRepo.findById(id).get();
    }

    @Override
    public SanPhamCT getOneByMa(String ma) {
        return sanPhamCTRepo.findByMaSPCT(ma);
    }

    @Override
    public Page<SanPhamCT> searchByFilter(String danhMucId, String kichThuocId, String mauSacId, String thuongHieuId, Pageable pageable) {
        return sanPhamCTRepo.findBySPDMIdAndKTIdAndMSIdAndSPTHId(danhMucId, kichThuocId, mauSacId, thuongHieuId, pageable);
    }

    @Override
    public Page<SanPhamCT> search(String keyword, Pageable pageable) {
        return sanPhamCTRepo.search(keyword, pageable);
    }

    @Override
    public Page<SanPhamCT> getAllBySP(String idSP, Pageable pageable) {
        return sanPhamCTRepo.findAllBySanPhamIdAndOrderByTenMS(idSP, pageable);
    }

    @Override
    public void update(SanPhamCT sanPhamCT, String id) {
        sanPhamCT.setId(id);
        sanPhamCTRepo.save(sanPhamCT);

    }

    @Override
    public Page<SanPhamCT> getAll(Pageable pageable) {
        return sanPhamCTRepo.findAll(pageable);
    }

    @Override
    public void add(SanPhamCT sanPhamCT) {
        sanPhamCTRepo.save(sanPhamCT);
    }

    @Override
    public SanPhamCT delete(String idSPCT) {
        Optional<SanPhamCT> optional=sanPhamCTRepo.findById(idSPCT);
        return optional.map(o->{
            sanPhamCTRepo.delete(o);
            return o;
        }).orElse(null);
    }
    @Override
    public SanPhamCT getByMSAndKT(String idKT, String idMS, String idSP) {
        return sanPhamCTRepo.findByMSAndKT(idKT, idMS,idSP);
    }

    @Override
    public Page<SanPhamCT> getByMSAndKTSPCT(String kichThuocId, String mauSacId, String sanPhamId, Pageable pageable) {
        return sanPhamCTRepo.findByMSAndKTSPCT(kichThuocId, mauSacId, sanPhamId, pageable);
    }

    @Override
    public Page<SanPhamCT> searchbyKeyWord(String keyword, Pageable pageable) {
        return sanPhamCTRepo.searchbyKeyWord(keyword, pageable);
    }


}
