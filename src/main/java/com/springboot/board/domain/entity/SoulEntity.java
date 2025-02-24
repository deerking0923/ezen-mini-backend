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

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "int default 0")
    private int rerunCount;

    @Column(length = 255)
    private String locationImage;

    @ElementCollection
    @CollectionTable(name = "soul_gesture_gifs", joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "gif_url")
    private List<String> gestureGifs;

    @ElementCollection
    @CollectionTable(name = "soul_wearing_shot_images", joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "image_url")
    private List<String> wearingShotImages;

    @ElementCollection
    @CollectionTable(name = "soul_keywords", joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @ElementCollection
    @CollectionTable(name = "soul_center_nodes", joinColumns = @JoinColumn(name = "soul_id"))
    private List<SoulNode> centerNodes;

    @ElementCollection
    @CollectionTable(name = "soul_left_side_nodes", joinColumns = @JoinColumn(name = "soul_id"))
    private List<SoulNode> leftSideNodes;

    @ElementCollection
    @CollectionTable(name = "soul_right_side_nodes", joinColumns = @JoinColumn(name = "soul_id"))
    private List<SoulNode> rightSideNodes;
    
    // 추가된 필드
    @Column(length = 255)
    private String creator; // 제작자 명
    
    @Column(columnDefinition = "TEXT")
    private String description; // 설명
}
