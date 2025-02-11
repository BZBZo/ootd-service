package com.example.spring.bzootdservice.repository;

import com.example.spring.bzootdservice.dto.OotdResponseDTO;
import com.example.spring.bzootdservice.entity.Ootd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OotdRepository extends JpaRepository<Ootd, Long> {
    @Query("SELECT o.heartNum FROM Ootd o WHERE o.id = :id")
    Integer findHeartNumById(@Param("id") Long id);

    // 특정 OOTD 게시물의 좋아요 개수 증가
    @Modifying
    @Query("UPDATE Ootd o SET o.heartNum = o.heartNum + 1 WHERE o.id = :id")
    void plusHeartNum(@Param("id") Long id);

    // 특정 OOTD 게시물의 좋아요 개수 감소 (최소 0)
    @Modifying
    @Query("UPDATE Ootd o SET o.heartNum = CASE WHEN o.heartNum > 0 THEN o.heartNum - 1 ELSE 0 END WHERE o.id = :id")
    void minusHeartNum(@Param("id") Long id);

    @Query(value = "SELECT * FROM ootd ORDER BY createdAt DESC LIMIT :limit", nativeQuery = true)
    List<Ootd> getRecentOotds(@Param("limit") int limit);

    // memberNo에 해당하는 OOTD 목록을 가져오는 메서드
    List<Ootd> findByMemberNo(Long memberNo);

}