package com.example.spring.bzootdservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OotdWriteRequestDto {
    private String title;
    private String content;
    private String relProd; // 관련 상품 ID
    private MultipartFile image; // 이미지 파일
}