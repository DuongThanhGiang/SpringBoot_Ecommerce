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


    @Override
    public List<KichThuoc> findAllByTrangThai() {
        return kichThuocRepo.findAllByTrangThai(1);
    }


}

