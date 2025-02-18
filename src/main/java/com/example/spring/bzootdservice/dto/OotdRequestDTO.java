package com.example.spring.bzootdservice.dto;


import lombok.Data;


@Data
public class OotdRequestDTO {
    private Long memberNo;  // 작성자 ID
    private String tags;     // 게시글 제목
    private String imgUrls;  // 업로드된 이미지 경로
    private String relProd;   // 관련 상품 ID 문자열 (콤마로 구분)
    private String timestamp; // 작성 시간
}