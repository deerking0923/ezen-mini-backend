package com.springboot.board.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class YurangDan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 id

    // 몇차인지 (고유 id와 별개)
    private int roundNumber;

    // 대표 노드 사진
    @Column(length = 255)
    private String representativeImage;

    // 위치 사진
    @Column(length = 255)
    private String locationImage;

    // 시작 날짜
    private LocalDate startDate;

    // 끝난 날짜
    private LocalDate endDate;

    // 출처 URL
    @Column(length = 255)
    private String sourceUrl;

    // 키워드 배열 (최대 15개 제한)
    @ElementCollection
    @CollectionTable(name = "yurangdan_keywords", joinColumns = @JoinColumn(name = "yurangdan_id"))
    @Column(name = "keyword", length = 100)
    private List<String> keywords;

    // 유랑 영혼 배열
    @ElementCollection
    @CollectionTable(name = "yurang_souls", joinColumns = @JoinColumn(name = "yurangdan_id"))
    private List<YurangSoul> yurangSouls;
}
