package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, UUID> {
    GioHang findByKhachHang(KhachHang khachHang);

}
