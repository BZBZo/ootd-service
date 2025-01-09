package com.example.spring.bzootdservice.service;

import com.example.spring.bzootdservice.dto.OotdRequestDTO;
import com.example.spring.bzootdservice.dto.OotdResponseDTO;
import com.example.spring.bzootdservice.dto.OotdWriteRequestDto;
import com.example.spring.bzootdservice.entity.Ootd;
import com.example.spring.bzootdservice.repository.OotdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OotdService {
    private final OotdRepository ootdRepository;
    private final FileStorageService fileStorageService;

    // FileStorageService 주입
    @Value("${bzbzo.bz-ootd-service-url}")
    private String ootdServiceBaseUrl; // 환경 변수로 URL을 가져옴

    public List<OotdResponseDTO> getAllOotds() {
        return ootdRepository.findAll()
                .stream()
                .map(ootd -> new OotdResponseDTO(
                        ootd.getId(),
                        ootd.getCustomerId(),
                        combineUrl(ootdServiceBaseUrl, ootd.getImage()), // 슬래시 중복 방지
                        ootd.getTitle(),
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



    public void createOotd(OotdRequestDTO ootdRequestDTO, MultipartFile image, String authorization) {
        // 이미지 저장
        String imagePath = fileStorageService.storeFile(image);

        // Ootd 엔티티 생성 및 저장
        Ootd ootd = Ootd.builder()
                .customerId(ootdRequestDTO.getCustomerId()) // 작성자 ID
                .title(ootdRequestDTO.getTitle())           // 제목
                .image(imagePath)                          // 이미지 경로
                .relProd(ootdRequestDTO.getRelProd())       // 관련 상품 ID
                .createdAt(LocalDateTime.now())            // 작성 시간
                .build();

        // 데이터베이스 저장
        ootdRepository.save(ootd);
    }


    }



