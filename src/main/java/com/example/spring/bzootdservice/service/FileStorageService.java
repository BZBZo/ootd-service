package com.example.spring.bzootdservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    public String storeFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetPath = Path.of("uploads").resolve(fileName);

            // 디렉토리 생성
            Files.createDirectories(targetPath.getParent());

            // 파일 저장
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다. 파일 이름: " + file.getOriginalFilename(), ex);
        }
    }
}
