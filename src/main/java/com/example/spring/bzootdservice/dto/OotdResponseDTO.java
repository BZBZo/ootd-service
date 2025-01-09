package com.example.spring.bzootdservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OotdResponseDTO {
    private Long id;
    private Long customerId;
    private String image;
    private String title;
    private String relProd;
    private Integer heartNum;
    private LocalDateTime createdAt;
}
