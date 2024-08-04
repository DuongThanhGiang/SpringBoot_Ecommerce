package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepo extends JpaRepository<ThuongHieu, String> {


    List<ThuongHieu> findAllByTrangThai(int trangThai);

}
