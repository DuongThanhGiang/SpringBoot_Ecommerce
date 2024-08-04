package com.springboot.bootstrap.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GioHangDTO {
    private UUID idspct;
    private Integer soLuong;
}
