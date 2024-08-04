package com.springboot.bootstrap.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ThoiGianHelper {
    public static List<String> danhSachNgay(LocalDate tuNgay, LocalDate denNgay) {
        LocalDate homNay = LocalDate.now();
        if (denNgay == null) {
            denNgay = homNay;
        }
        if (tuNgay == null) {
            tuNgay = denNgay.minusMonths(1);
        }

        List<String> danhSachNgay = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (!tuNgay.isAfter(denNgay)) {
            String ngayThangNam = tuNgay.format(formatter);
            danhSachNgay.add(ngayThangNam);
            tuNgay = tuNgay.plusDays(1);
        }

        return danhSachNgay;
    }

//    public static String titleSoDon(LocalDate tuNgay, LocalDate denNgay){
//        return "Thống Kê Số Đơn Đặt Hàng Từ Ngày"+ tuNgay + " - " + denNgay;
//    }
//    public static String titleSoSanPham(LocalDate tuNgay, LocalDate denNgay){
//        return "Thống Kê Số Sản Phẩm Đã Bán Từ Ngày"+ tuNgay + " - " + denNgay;
//    }
//    public static String titleDoanhThu(LocalDate tuNgay, LocalDate denNgay){
//        return "Thống Kê Doanh Thu Từ Ngày"+ tuNgay + " - " + denNgay;
//    }

    public static List<String> danhSachThang(LocalDate tuNgay, LocalDate denNgay) {
        List<String> danhSachThang = new ArrayList<>();
        LocalDate homNay = LocalDate.now();
        if (denNgay == null) {
            denNgay = homNay;
        }
        if (tuNgay == null) {
            tuNgay = denNgay.minusYears(1);
        }


        while (!tuNgay.isAfter(denNgay)) {
            String thang = tuNgay.getMonthValue() + "/" + tuNgay.getYear();
            danhSachThang.add(thang);
            tuNgay = tuNgay.plusMonths(1);
        }

        return danhSachThang;
    }

    public static List<String> danhSachNam(LocalDate tuNgay, LocalDate denNgay) {

        LocalDate homNay=LocalDate.now();
        System.out.println("Từ Ngày: "+tuNgay);
        System.out.println("Đến Ngày: "+tuNgay);
        Integer namBatDau=null;
        Integer namKetThuc=null;

        List<String> danhSachNam = new ArrayList<>();
        if (tuNgay!=null){//tu ko null
            namBatDau = tuNgay.getYear();
            if (denNgay!=null) {//denko null
                 namKetThuc = denNgay.getYear();
            }else {
                 namKetThuc = homNay.getYear();
            }
        }else{//tu null
            if (denNgay!=null) {//den ko null
                Integer namDefault=denNgay.getYear()-4;
                LocalDate batdau = LocalDate.of(namDefault, 1, 1);
                 namBatDau = batdau.getYear();
                 namKetThuc = denNgay.getYear();
            }else {//den null
                Integer namDefault=homNay.getYear()-4;
                LocalDate batdau = LocalDate.of(namDefault, 1, 1);
                namBatDau = batdau.getYear();
                namKetThuc = homNay.getYear();
            }
        }
        for ( Integer nam = namBatDau; nam <= namKetThuc; nam++) {
            danhSachNam.add(String.valueOf(nam));
        }

        return danhSachNam;
    }

    public static List<LocalDate> danhSachNgayLocalDate(LocalDate tuNgay, LocalDate denNgay) {
        LocalDate homNay = LocalDate.now();
        if (denNgay == null) {
            denNgay = homNay;
        }
        if (tuNgay == null) {
            tuNgay = denNgay.minusMonths(1);
        }

        List<LocalDate> danhSachNgay = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (!tuNgay.isAfter(denNgay)) {
            danhSachNgay.add(tuNgay);
            tuNgay = tuNgay.plusDays(1);
        }
        return danhSachNgay;
    }

    public static List<LocalDate> danhSachThangLocalDate(LocalDate tuNgay, LocalDate denNgay) {
        List<LocalDate> danhSachThang = new ArrayList<>();
        LocalDate homNay = LocalDate.now();
        if (denNgay == null) {
            denNgay = homNay;
        }
        if (tuNgay == null) {
            tuNgay = denNgay.minusYears(1);
        }


        while (!tuNgay.isAfter(denNgay)) {
            danhSachThang.add(tuNgay);
            tuNgay = tuNgay.plusMonths(1);
        }

        return danhSachThang;
    }

    public static List<LocalDate> danhSachNamLocalDate(LocalDate tuNgay, LocalDate denNgay) {
        LocalDate homNay = LocalDate.now();
        System.out.println("Từ Ngày: " + tuNgay);
        System.out.println("Đến Ngày: " + denNgay);
        Integer namBatDau = null;
        Integer namKetThuc = null;

        List<LocalDate> danhSachNam = new ArrayList<>();
        if (tuNgay != null) { // tu ko null
            namBatDau = tuNgay.getYear();
            if (denNgay != null) { // den ko null
                namKetThuc = denNgay.getYear();
            } else {
                namKetThuc = homNay.getYear();
            }
        } else { // tu null
            if (denNgay != null) { // den ko null
                Integer namDefault = denNgay.getYear() - 4;
                LocalDate batdau = LocalDate.of(namDefault, 1, 1);
                namBatDau = batdau.getYear();
                namKetThuc = denNgay.getYear();
            } else { // den null
                Integer namDefault = homNay.getYear() - 4;
                LocalDate batdau = LocalDate.of(namDefault, 1, 1);
                namBatDau = batdau.getYear();
                namKetThuc = homNay.getYear();
            }
        }
        for (Integer nam = namBatDau; nam <= namKetThuc; nam++) {
            danhSachNam.add(LocalDate.of(nam, 1, 1));
        }

        return danhSachNam;
    }

    public static void main(String[] args) {
        LocalDate tuNgay = LocalDate.of(2023, 1, 1);
        LocalDate denNgay = LocalDate.of(2024, 6, 10);

        List<String> danhSachNgay = ThoiGianHelper.danhSachThang(tuNgay, null);
        List<LocalDate> danhSachNgayLocalDate = ThoiGianHelper.danhSachThangLocalDate(tuNgay, denNgay);

        for (LocalDate ngay : danhSachNgayLocalDate) {
            System.out.println("Ngày: " + ngay);
        }
//        for (String ngay : danhSachNgay) {
//            System.out.println("Ngày: " + ngay);
//        }
    }
}
