package com.example.spring.bzootdservice.repository;

import com.example.spring.bzootdservice.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart,Long> {
    boolean existsByMemberNoAndOotdId(Long memberNo, Long ootdId);

    Optional<Heart> findByMemberNoAndOotdId(Long memberNo, Long ootdId);
}
