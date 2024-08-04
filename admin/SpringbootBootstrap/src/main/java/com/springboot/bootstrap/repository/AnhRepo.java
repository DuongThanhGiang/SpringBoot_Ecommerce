package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnhRepo extends JpaRepository<Anh, String> {
    List<Anh> findAllBySanPham(SanPham sanPham);
}
