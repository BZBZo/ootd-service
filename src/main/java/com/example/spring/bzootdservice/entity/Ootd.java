package com.example.spring.bzootdservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "ootd")
public class Ootd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberNo;

    private String imgUrls;

    private String tags;

    private String relProd;

    private Integer heartNum;

    private LocalDateTime createdAt;
}
