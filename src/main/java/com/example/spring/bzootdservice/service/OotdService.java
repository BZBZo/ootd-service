package com.example.spring.bzootdservice.service;

import com.example.spring.bzootdservice.dto.OotdResponseDTO;
import com.example.spring.bzootdservice.entity.Heart;
import com.example.spring.bzootdservice.entity.Ootd;
import com.example.spring.bzootdservice.repository.HeartRepository;
import com.example.spring.bzootdservice.repository.OotdRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OotdService {
    private final OotdRepository ootdRepository;
    private final ImgServiceImpl imgServiceImpl;
    private final HeartRepository heartRepository;


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
                .heartNum(0)
                .build();

        // 데이터베이스 저장
        ootdRepository.save(ootd);
    }


    public boolean isUserLikedOotd(Long memberNo, Long ootdId) {
        return heartRepository.existsByMemberNoAndOotdId(memberNo, ootdId);
    }

    public int getHeartNum(Long ootdId) {
        return ootdRepository.findHeartNumById(ootdId);
    }

    @Transactional
    public boolean toggleLike(Long memberNo, Long ootdId) {
        Optional<Heart> existingLike = heartRepository.findByMemberNoAndOotdId(memberNo, ootdId);

        if (existingLike.isPresent()) {
            // 좋아요가 이미 눌려있다면 삭제 (Unlike)
            heartRepository.delete(existingLike.get());
            heartRepository.flush();  // ✅ 즉시 DB에 반영
            updateHeartNum(ootdId, false);
            return false; // 좋아요 취소 후 false 반환
        } else {
            // 좋아요가 없으면 추가 (Like)
            Heart newLike = Heart.builder()
                    .memberNo(memberNo)
                    .ootdId(ootdId)
                    .build();
            heartRepository.save(newLike);
            heartRepository.flush();  // ✅ 즉시 DB에 반영
            updateHeartNum(ootdId, true);
            return true; // 좋아요 추가 후 true 반환
        }
    }

    public void updateHeartNum(Long ootdId, boolean like){
        if(like){
            System.out.println("✅ 좋아요 추가! ootdId: " + ootdId);
            ootdRepository.plusHeartNum(ootdId);
        } else {
            System.out.println("✅ 좋아요 취소! ootdId: " + ootdId);
            ootdRepository.minusHeartNum(ootdId);
        }
    }

    public List<OotdResponseDTO> getRecentOotds(int i) {
        return ootdRepository.getRecentOotds(i)
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

    public List<OotdResponseDTO> getOotdsByUserId(Long userId) {
        // userId에 해당하는 OOTD 데이터를 조회
        return ootdRepository.findByMemberNo(userId)
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

}



