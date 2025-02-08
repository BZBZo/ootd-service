package com.example.spring.bzootdservice.controller;

import com.example.spring.bzootdservice.dto.OotdResponseDTO;
import com.example.spring.bzootdservice.service.OotdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ootd")
public class OotdApiController {
    private final OotdService ootdService;

    @GetMapping
    public List<OotdResponseDTO> getOotdList() {
        List<OotdResponseDTO> ootdList = ootdService.getOotdList();
        return ootdList;
    }

    @GetMapping("/heart/num")
    int getHeartNum(@RequestParam Long ootdId) {
        return ootdService.getHeartNum(ootdId);
    }

    @GetMapping("/like/history")
    boolean isUserLikedOotd(@RequestParam Long memberNo, @RequestParam Long id){
        return ootdService.isUserLikedOotd(memberNo, id);
    }

    @PostMapping("/heart")
    boolean toggleLike(@RequestParam Long memberNo, @RequestParam Long ootdId){
        return ootdService.toggleLike(memberNo, ootdId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createOotd(
            @RequestParam("memberNo") Long memberNo,
            @RequestParam("tags") String tags,
            @RequestParam("relProd") String relProd,
            @RequestPart("image") MultipartFile image,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            System.out.println("memberNo: " + memberNo + "tags: " + tags + "relProd: " + relProd);
            ootdService.createOotd(memberNo, tags, relProd, image, authorization);
            return ResponseEntity.ok("OOTD 작성이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("OOTD 작성 중 오류가 발생했습니다.");
        }
    }

}
