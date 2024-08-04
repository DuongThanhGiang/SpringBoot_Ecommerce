package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepo extends JpaRepository<DanhMuc,String> {

    List<DanhMuc> findAllByTrangThai(int trangThai);

}
