package com.example.spring.bzootdservice.repository;

import com.example.spring.bzootdservice.entity.Ootd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OotdRepository extends JpaRepository<Ootd, Long> {
}