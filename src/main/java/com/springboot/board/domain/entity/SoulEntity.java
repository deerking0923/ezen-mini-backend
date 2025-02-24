package com.springboot.board.domain.entity;

import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SoulEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = false)
    private String seasonName;

    @Column(length = 255)
    private String representativeImage;

    @Column(length = 255, nullable = false)
    private String name;

    // 시작 날짜와 마감 날짜를 DATE 타입으로 관리
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "int default 0")
    private int rerunCount;

    @Column(length = 255)
    private String locationImage;

    // 단일 컬럼 대신 제스처 GIF는 컬렉션으로 관리
    @ElementCollection
    @CollectionTable(name = "soul_gesture_gifs", joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "gif_url")
    private List<String> gestureGifs;

    // 착용샷 이미지 (여러 개)
    @ElementCollection
    @CollectionTable(name = "soul_wearing_shot_images", joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "image_url")
    private List<String> wearingShotImages;

    // 키워드 배열
    @ElementCollection
    @CollectionTable(name = "soul_keywords", joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    // 중앙 노드 (옵션, 최대 7개)
    @ElementCollection
    @CollectionTable(name = "soul_center_nodes", joinColumns = @JoinColumn(name = "soul_id"))
    private List<SoulNode> centerNodes;

    // 왼쪽 사이드 노드 (옵션, 최대 6개)
    @ElementCollection
    @CollectionTable(name = "soul_left_side_nodes", joinColumns = @JoinColumn(name = "soul_id"))
    private List<SoulNode> leftSideNodes;

    // 오른쪽 사이드 노드 (옵션, 최대 6개)
    @ElementCollection
    @CollectionTable(name = "soul_right_side_nodes", joinColumns = @JoinColumn(name = "soul_id"))
    private List<SoulNode> rightSideNodes;
}
