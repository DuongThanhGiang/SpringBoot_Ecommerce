package com.springboot.bootstrap.entity.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SanPhamDTO {
    private String idSP;
    private String ten;
    private String trangThai;
    private String thuongHieu;
    private String danhMuc;
    private MultipartFile[] data;
    private String[] idMSAr;
    private String[] idKTAr;
}
