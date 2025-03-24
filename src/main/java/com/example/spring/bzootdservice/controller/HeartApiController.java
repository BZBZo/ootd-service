package com.example.spring.bzootdservice.controller;

import com.example.spring.bzootdservice.service.OotdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ootd")
public class HeartApiController {
    private final OotdService ootdService;

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
}
