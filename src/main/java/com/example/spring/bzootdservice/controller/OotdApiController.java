package com.example.spring.bzootdservice.controller;

import com.example.spring.bzootdservice.dto.OotdRequestDTO;
import com.example.spring.bzootdservice.dto.OotdResponseDTO;
import com.example.spring.bzootdservice.service.OotdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ootd")
public class OotdApiController {
    private final OotdService ootdService;

    @GetMapping
    public ResponseEntity<List<OotdResponseDTO>> getOotdList() {
        List<OotdResponseDTO> ootdList = ootdService.getAllOotds();
        return ResponseEntity.ok(ootdList);
    }

    @PostMapping("/write")
    public ResponseEntity<String> createOotd(
            @RequestPart("ootd") OotdRequestDTO ootdRequestDTO,
            @RequestPart("image") MultipartFile image,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            ootdService.createOotd(ootdRequestDTO, image, authorization);
            return ResponseEntity.ok("OOTD 작성이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("OOTD 작성 중 오류가 발생했습니다.");
        }
    }

}
