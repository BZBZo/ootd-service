package com.example.spring.bzootdservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImgService {
    public String uploadImg(String name, MultipartFile file);
}
