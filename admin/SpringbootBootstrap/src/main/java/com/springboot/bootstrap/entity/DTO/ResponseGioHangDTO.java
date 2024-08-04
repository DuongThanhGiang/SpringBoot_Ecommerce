package com.springboot.bootstrap.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ResponseGioHangDTO {
    private Integer status;
    private String idspct;
    private Integer soLuong;
}
