package com.springboot.bootstrap.controller.sanphamcontroller;

import com.springboot.bootstrap.entity.Anh;
import com.springboot.bootstrap.entity.DanhMuc;
import com.springboot.bootstrap.entity.KichThuoc;
import com.springboot.bootstrap.entity.MauSac;
import com.springboot.bootstrap.entity.NhanVien;
import com.springboot.bootstrap.entity.SanPham;
import com.springboot.bootstrap.entity.SanPhamCT;
import com.springboot.bootstrap.entity.ThuongHieu;
import com.springboot.bootstrap.repository.AnhRepo;
import com.springboot.bootstrap.repository.SanPhamRepo;
import com.springboot.bootstrap.service.AnhService;
import com.springboot.bootstrap.service.DanhMucService;
import com.springboot.bootstrap.service.NhanVienService;
import com.springboot.bootstrap.service.SanPhamService;
import com.springboot.bootstrap.service.ThuongHieuService;
import com.springboot.bootstrap.utility.Base64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/san_pham")
public class SanPhamController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private DanhMucService danhMucService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private Base64Image base64Image;
    @Autowired
    private AnhRepo anhRepo;
    @Autowired
    private AnhService anhService;

    @GetMapping("")
    public String getAll(@RequestParam("p") Optional<Integer> p, Model model) {
        Page<SanPham> listSP = sanPhamService.getAll(PageRequest.of(p.orElse(0), 5));
        Map<String, List<Anh>> mapAnhSanPham = new HashMap<>();
        for (SanPham sanPham : listSP) {
            List<Anh> listAnh = anhRepo.findAllBySanPham(sanPham);
            mapAnhSanPham.put(sanPham.getId(), listAnh);
        }
        List<DanhMuc> listDM = danhMucService.findAllByTrangThai();
        List<ThuongHieu> listTH = thuongHieuService.findAllByTrangThai();
        model.addAttribute("listTH", listTH);
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("listDM", listDM);
        model.addAttribute("listSP", listSP);
        model.addAttribute("mapAnhSanPham", mapAnhSanPham);
        return "/pages/san_pham";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(value = "th",required = false) String idTH,
                         @RequestParam(value = "dm",required = false) String idDM,
                         @RequestParam(value = "trangThai",required = false) Integer trangThai,
                         @RequestParam(value = "keyword",required = false) String keyword,
                         @RequestParam("p") Optional<Integer> p, Model model) {
        Page<SanPham> listSP = sanPhamRepo.filter(idDM,idTH,trangThai,keyword,PageRequest.of(p.orElse(0), 5));
        Map<String, List<Anh>> mapAnhSanPham = new HashMap<>();
        for (SanPham sanPham : listSP) {
            List<Anh> listAnh = anhRepo.findAllBySanPham(sanPham);
            mapAnhSanPham.put(sanPham.getId(), listAnh);
        }
        List<DanhMuc> listDM = danhMucService.findAllByTrangThai();
        List<ThuongHieu> listTH = thuongHieuService.findAllByTrangThai();
        model.addAttribute("listTH", listTH);
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("listDM", listDM);
        model.addAttribute("listSP", listSP);
        model.addAttribute("mapAnhSanPham", mapAnhSanPham);
        return "/pages/san_pham";
    }

    @GetMapping("/viewOne/")
    @ResponseBody
    public SanPham viewUpdate(String id) {

        return sanPhamService.detail(id);
    }

    @GetMapping("/viewImage/")
    @ResponseBody
    public List<Anh> viewImage(String idSP) {
        SanPham sanPham = sanPhamService.detail(idSP);
        return anhRepo.findAllBySanPham(sanPham);
    }
    @PostMapping("/addImage")
    public ResponseEntity<List<Anh>> addSanPham(
            @RequestParam("idSP") String idSP,
            @RequestParam("file") MultipartFile[] files) {
        SanPham sanPham = sanPhamService.detail(idSP);
        List<Anh> listImgAdd = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Anh anh = Anh.builder()
                        .sanPham(sanPham)
                        .data(file.getBytes())
                        .build();
                anhService.add(anh);
                listImgAdd.add(anh);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(listImgAdd);
    }
    @PostMapping("/updateImage")
    public ResponseEntity<Anh> updateImage(
            @RequestParam("idImg") String idImg,
            @RequestParam("file") MultipartFile files) {
        Anh anh = anhRepo.findById(idImg).get();
        try {
            anh.setData(files.getBytes());
            anhRepo.save(anh);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(anh);
    }


    @GetMapping("/deleteImage/{id}")
    @ResponseBody
    public ResponseEntity<?> removeImage(@PathVariable("id") String idImg) {

        return ResponseEntity.ok(anhService.delete(idImg));
    }

    @PostMapping("/updateSP")
    public String updateSP(@ModelAttribute("spu") SanPham sanPham,
                           @RequestParam("id") String id,
                           @RequestParam("ma") String ma,
                           @RequestParam("danhMuc") String idDM,
                           @RequestParam("taoLuc") LocalDateTime taoLuc,
                           @RequestParam("taoBoi") String taoBoi,
                           @RequestParam("thuongHieu") String idTH,
                           @RequestParam("ten") String tenSP,
                           @RequestParam(value = "trangThaiSt", required = false) String trangThaiSt, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NhanVien nhanVien = nhanVienService.getOne(userDetails.getUsername());
        int trangThai = (trangThaiSt != null && trangThaiSt.equals("on")) ? 1 : 0;
        sanPham = SanPham.builder()
                .ma(ma)
                .ten(tenSP)
                .danhMuc(DanhMuc.builder().id(idDM).build())
                .thuongHieu(ThuongHieu.builder().id(idTH).build())
                .taoLuc(taoLuc)
                .suaLuc(LocalDateTime.now())
                .taoBoi(taoBoi)
                .suaBoi(nhanVien.getTen())
                .trangThai(trangThai).build();

        sanPhamService.update(id, sanPham);
        return "redirect:/san_pham";
    }
    @GetMapping("/convertToBase64")
    @ResponseBody
    public String ViewImg(@RequestParam("id") String id) {
        Anh anh = anhRepo.findById(id).get();
        byte[] imageData = anh.getData();
        String base64Data = base64Image.bytesToBase64(imageData);
        return base64Data;
    }

}
