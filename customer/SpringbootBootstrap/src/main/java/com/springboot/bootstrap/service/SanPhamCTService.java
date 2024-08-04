package com.springboot.bootstrap.service;

import com.springboot.bootstrap.entity.SanPhamCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanPhamCTService {
    Page<SanPhamCT> searchByFilter(String danhMucId, String kichThuocId, String mauSacId, String thuongHieuId, Pageable pageable);

    SanPhamCT getOneByMa(String ma);

    SanPhamCT delete(String idSPCT);

    void add(SanPhamCT sanPhamCT);

    SanPhamCT getOne(String id);

    void update(SanPhamCT sanPhamCT, String id);

    Page<SanPhamCT> getAllBySP(String idSP, Pageable pageable);

    SanPhamCT getByMSAndKT(String idKT,String idMS,String idSP);

    public Page<SanPhamCT> search(String keyword, Pageable pageable);

    Page<SanPhamCT> getAll(Pageable pageable);

    Page<SanPhamCT> getBySL(Pageable pageable);

    Page<SanPhamCT> getByMSAndKTSPCT( String kichThuocId, String mauSacId, String sanPhamId, Pageable pageable);

    Page<SanPhamCT> searchbyKeyWord(String keyword, Pageable pageable);


}
