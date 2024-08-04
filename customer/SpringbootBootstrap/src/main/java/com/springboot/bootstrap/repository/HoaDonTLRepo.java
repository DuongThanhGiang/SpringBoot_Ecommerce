package com.springboot.bootstrap.repository;


import com.springboot.bootstrap.entity.HoaDon;
import com.springboot.bootstrap.entity.HoaDonTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonTLRepo extends JpaRepository<HoaDonTimeline, String> {
    List<HoaDonTimeline> findAllByHoaDonOrderByNgayTaoAsc(HoaDon hoaDon);
}
