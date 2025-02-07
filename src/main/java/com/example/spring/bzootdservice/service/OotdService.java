package com.example.spring.bzootdservice.service;

import com.example.spring.bzootdservice.dto.OotdResponseDTO;
import com.example.spring.bzootdservice.entity.Ootd;
import com.example.spring.bzootdservice.repository.OotdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OotdService {
    private final OotdRepository ootdRepository;
    private final ImgServiceImpl imgServiceImpl;



    public List<OotdResponseDTO> getOotdList() {
        return ootdRepository.findAll()
                .stream()
                .map(ootd -> new OotdResponseDTO(
                        ootd.getId(),
                        ootd.getMemberNo(),
                        ootd.getImgUrls(), // 슬래시 중복 방지
                        ootd.getTags(),
                        ootd.getRelProd(),
                        ootd.getHeartNum(),
                        ootd.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }



    /**
     * Base URL과 경로를 합칠 때 슬래시 중복을 방지하는 메서드
     */
    private String combineUrl(String baseUrl, String path) {
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1); // 끝의 / 제거
        }
        if (path.startsWith("/")) {
            path = path.substring(1); // 시작의 / 제거
        }
        return baseUrl + "/" + path;
    }



    public void createOotd(Long memberNo, String tags, String relProd, MultipartFile image, String authorization) {

        // S3에 업로드할 고유한 파일 이름 생성
        String uniqueFileName = "static/bz-image/" + UUID.randomUUID();

        // S3에 이미지 업로드
        String imgUrl = imgServiceImpl.uploadImg(uniqueFileName, image);


//        // 이미지 저장
//        String imagePath = fileStorageService.storeFile(image);

        // Ootd 엔티티 생성 및 저장
        Ootd ootd = Ootd.builder()
                .memberNo(memberNo) // 작성자 ID
                .tags(tags)           // 태그
                .imgUrls(imgUrl)                          // 이미지 경로
                .relProd(relProd)       // 관련 상품 ID
                .createdAt(LocalDateTime.now())            // 작성 시간
                .build();

        // 데이터베이스 저장
        ootdRepository.save(ootd);
    }


    }



