package com.springboot.bootstrap.controller.sanphamcontroller;

import com.springboot.bootstrap.entity.DTO.GioHangAddDTO;
import com.springboot.bootstrap.entity.GioHang;
import com.springboot.bootstrap.entity.GioHangChiTiet;
import com.springboot.bootstrap.entity.KhachHang;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.repository.GioHangChiTietRepository;
import com.springboot.bootstrap.repository.GioHangRepository;
import com.springboot.bootstrap.repository.SanPhamCTRepo;
import com.springboot.bootstrap.service.KhachHangService;
import com.springboot.bootstrap.service.SanPhamCTService;
import com.springboot.bootstrap.service.SanPhamService;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/shop")
public class SanPhamDetailOnlineController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamCTRepo sanPhamCTRepo;

    @Autowired
    private SanPhamCTService sanPhamCTService;
    @Autowired
    private Base64Image base64Image;

    @Autowired
    private GioHangRepository gioHangRepo;
    @Autowired
    private GioHangChiTietRepository gioHangCTRepo;
    @Autowired
    private KhachHangService khachHangService;






    @GetMapping("/findAllGHCT")
    @ResponseBody
    public ResponseEntity<Page<GioHangChiTiet>> spct(@RequestParam("p") Optional<Integer> p) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String idKH = userDetails.getUsername();
        KhachHang khachHang = khachHangService.getOne(idKH);
        Page<GioHangChiTiet> listGHCT = gioHangCTRepo.findAllByKH(khachHang, PageRequest.of(p.orElse(0), 3));
        return ResponseEntity.ok(listGHCT);
    }

    @GetMapping("/getAllGHCT")
    @ResponseBody
    public ResponseEntity<List<GioHangChiTiet>> allGHCTList() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String idKH = userDetails.getUsername();
        KhachHang khachHang = khachHangService.getOne(idKH);
        List<GioHangChiTiet> listGHCT = gioHangCTRepo.getAllByKhachHang(khachHang);
        return ResponseEntity.ok(listGHCT);
    }

    @GetMapping("/getGHCTBySPCT")
    @ResponseBody
    public ResponseEntity<GioHangChiTiet> validateSL(@RequestParam("idSPCT") String idSPCT) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String idKH = userDetails.getUsername();
        KhachHang khachHang = khachHangService.getOne(idKH);
        GioHangChiTiet ghct = gioHangCTRepo.getBySPCT(idSPCT, khachHang);
        return ResponseEntity.ok(ghct);
    }

    @PostMapping("/addGH")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSanPham(@RequestBody GioHangAddDTO gioHangAddDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String idKH = userDetails.getUsername();
        KhachHang khachHang = khachHangService.getOne(idKH);
        GioHang gh = gioHangRepo.findByKhachHang(khachHang);
        SanPhamCT sanPhamCT = sanPhamCTRepo.findById(gioHangAddDTO.getIdSPCT()).get();

        boolean isUpdated = false;
        List<GioHangChiTiet> listGHCT = gioHangCTRepo.getAllByKhachHang(khachHang);
        if (listGHCT != null) {
            for (GioHangChiTiet ghctUp : listGHCT) {
                if (ghctUp.getSanPhamCT().getId().equals(sanPhamCT.getId())) {
                    ghctUp.setSoLuong(ghctUp.getSoLuong() + gioHangAddDTO.getSoLuong());
                    ghctUp.setDonGia(ghctUp.getSoLuong() * sanPhamCT.getGia());
                    gioHangCTRepo.save(ghctUp);
                    isUpdated = true;
                    break;

                }


            }

        }
        if (!isUpdated) {
            GioHangChiTiet ghct = GioHangChiTiet.builder()
                    .gioHang(gh)
                    .sanPhamCT(sanPhamCT)
                    .soLuong(gioHangAddDTO.getSoLuong())
                    .donGia(sanPhamCT.getGia() * gioHangAddDTO.getSoLuong()).build();
            gioHangCTRepo.save(ghct);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Success");
        return ResponseEntity.ok(response);

    }
}


