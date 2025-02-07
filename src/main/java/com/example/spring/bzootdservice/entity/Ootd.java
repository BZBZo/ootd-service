package com.example.spring.bzootdservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ootd")
public class Ootd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "memberNo", nullable = false)
    private Long memberNo;

    @Column(name = "imgUrls", nullable = true)
    private String imgUrls;

    @Column(name = "title", nullable = true)
    private String tags;

    @Column(name = "rel_prod", nullable = true)
    private String relProd;

    @Column(name = "heart_num", nullable = true)
    private Integer heartNum;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
