package com.example.spring.bzootdservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class OotdResponseDTO {
    private Long id;
    private Long memberNo;
    private String imgUrls;
    private String tags;
    private String relProd;
    private Integer heartNum;
    private LocalDateTime createdAt;
}