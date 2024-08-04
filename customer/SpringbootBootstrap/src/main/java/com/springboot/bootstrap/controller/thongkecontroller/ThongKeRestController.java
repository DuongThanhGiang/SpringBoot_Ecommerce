package com.springboot.bootstrap.controller.thongkecontroller;

import com.springboot.bootstrap.entity.ThoiGianHelper;
import com.springboot.bootstrap.repository.HoaDonChiTietRepository;
import com.springboot.bootstrap.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class ThongKeRestController {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/y-so-don-hang")
    public List<Integer> getYSoDonHang(@RequestParam(value = "thoiGian",required = false) String thoiGian
            , @RequestParam(value = "tuNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay
            , @RequestParam(value = "denNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {
        List<Integer> listSoDonHang=new ArrayList<>();
        List<LocalDate> listNgay=ThoiGianHelper.danhSachNgayLocalDate(tuNgay,denNgay);
        List<LocalDate> listThang=ThoiGianHelper.danhSachThangLocalDate(tuNgay,denNgay);
        List<LocalDate> listNam=ThoiGianHelper.danhSachNamLocalDate(tuNgay,denNgay);
        if (thoiGian==null){
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listSoDonHang.add(hoaDonRepository.soDonHangTheoNgayTao(ngayFormatted));
            }
        }else if (thoiGian.equals("1")){
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listSoDonHang.add(hoaDonRepository.soDonHangTheoNgayTao(ngayFormatted));
            }
        }else if (thoiGian.equals("2")){
            for (LocalDate thang:listThang){
                Date thangFormatted=Date.valueOf(thang);
                listSoDonHang.add(hoaDonRepository.soDonHangTheoThangVaNam(thangFormatted));
            }
        }else if (thoiGian.equals("3")){
            for (LocalDate nam:listNam){
                Date namFormatted=Date.valueOf(nam);
                listSoDonHang.add(hoaDonRepository.soDonHangTheoNam(namFormatted));
            }
        }else {
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listSoDonHang.add(hoaDonRepository.soDonHangTheoNgayTao(ngayFormatted));
            }
        }
        return listSoDonHang;
    }

    @GetMapping("/y-so-san-pham")
    public List<Integer> getYSoSanPhamDaBan(@RequestParam(value = "thoiGian",required = false) String thoiGian
            , @RequestParam(value = "tuNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay
            , @RequestParam(value = "denNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {
        List<Integer> listSoSanPhamDaBan=new ArrayList<>();
        List<LocalDate> listNgay=ThoiGianHelper.danhSachNgayLocalDate(tuNgay,denNgay);
        List<LocalDate> listThang=ThoiGianHelper.danhSachThangLocalDate(tuNgay,denNgay);
        List<LocalDate> listNam=ThoiGianHelper.danhSachNamLocalDate(tuNgay,denNgay);
        if (thoiGian==null){
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listSoSanPhamDaBan.add(hoaDonChiTietRepository.soSanPhamDaBanTheoNgay(ngayFormatted));
            }
        }else if (thoiGian.equals("1")){
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listSoSanPhamDaBan.add(hoaDonChiTietRepository.soSanPhamDaBanTheoNgay(ngayFormatted));
            }
        }else if (thoiGian.equals("2")){
            for (LocalDate thang:listThang){
                Date thangFormatted=Date.valueOf(thang);
                listSoSanPhamDaBan.add(hoaDonChiTietRepository.soSanPhamDaBanTheoThangVaNam(thangFormatted));
            }
        }else if (thoiGian.equals("3")){
            for (LocalDate nam:listNam){
                Date namFormatted=Date.valueOf(nam);
                listSoSanPhamDaBan.add(hoaDonChiTietRepository.soSanPhamDaBanTheoNam(namFormatted));
            }
        }else {
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listSoSanPhamDaBan.add(hoaDonChiTietRepository.soSanPhamDaBanTheoNgay(ngayFormatted));
            }
        }
        return listSoSanPhamDaBan;
    }

    @GetMapping("/y-doanh-thu")
    public List<Integer> getYDoanhThu(@RequestParam(value = "thoiGian",required = false) String thoiGian
            , @RequestParam(value = "tuNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay
            , @RequestParam(value = "denNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {
        List<Integer> listDoanhThu=new ArrayList<>();
        List<LocalDate> listNgay=ThoiGianHelper.danhSachNgayLocalDate(tuNgay,denNgay);
        List<LocalDate> listThang=ThoiGianHelper.danhSachThangLocalDate(tuNgay,denNgay);
        List<LocalDate> listNam=ThoiGianHelper.danhSachNamLocalDate(tuNgay,denNgay);
        if (thoiGian==null){
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listDoanhThu.add(hoaDonRepository.doanhThuTheoNgayTao(ngayFormatted));
            }
        }else if (thoiGian.equals("1")){
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listDoanhThu.add(hoaDonRepository.doanhThuTheoNgayTao(ngayFormatted));
            }
        }else if (thoiGian.equals("2")){
            for (LocalDate thang:listThang){
                Date thangFormatted=Date.valueOf(thang);
                listDoanhThu.add(hoaDonRepository.doanhThuTheoThangVaNam(thangFormatted));
            }
        }else if (thoiGian.equals("3")){
            for (LocalDate nam:listNam){
                Date namFormatted=Date.valueOf(nam);
                listDoanhThu.add(hoaDonRepository.doanhThuTheoNam(namFormatted));
            }
        }else {
            for (LocalDate ngay:listNgay){
                Date ngayFormatted=Date.valueOf(ngay);
                listDoanhThu.add(hoaDonRepository.doanhThuTheoNgayTao(ngayFormatted));
            }
        }
        return listDoanhThu;
    }

    @GetMapping("/x")
    public List<? extends Object> getY(@RequestParam(value = "thoiGian",required = false) String thoiGian
            ,@RequestParam(value = "tuNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay
            ,@RequestParam(value = "denNgay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay) {
        ThoiGianHelper thoiGianHelper=new ThoiGianHelper();
        if (thoiGian==null){
            return thoiGianHelper.danhSachNgay(tuNgay,denNgay);
        }else if (thoiGian.equals("1")){
            return thoiGianHelper.danhSachNgay(tuNgay,denNgay);
        }else if (thoiGian.equals("2")){
            return thoiGianHelper.danhSachThang(tuNgay,denNgay);
        }else if (thoiGian.equals("3")){
            return thoiGianHelper.danhSachNam(tuNgay,denNgay);
        }
        return thoiGianHelper.danhSachNgay(tuNgay,denNgay);
    }
}
