package com.springboot.bootstrap.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class GioHangTqDTO {
    private UUID idHoaDonChiTiet;
    private String ma;
    private Integer soLuong;
}
