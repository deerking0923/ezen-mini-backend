package com.springboot.board.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Entity
@Table(name = "soul_entity")
public class SoulEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 시즌(테마) 이름 */
    @Column(length = 255, nullable = false)
    private String seasonName;

    /** 영혼 이름(한/영 통합) */
    @Column(length = 255, nullable = false)
    private String name;

    /** 시즌 내 등장 순서 */
    @Column(name = "order_num", nullable = false)
    private int orderNum;

    /** 등장 기간 */
    @Column(nullable = false) private LocalDate startDate;
    @Column(nullable = false) private LocalDate endDate;

    /** 누적 복각(재등장) 횟수 */
    @Column(columnDefinition = "int default 0")
    private int rerunCount;

    /** 키워드 (최대 15개) */
    @ElementCollection
    @CollectionTable(name = "soul_keywords",
                     joinColumns = @JoinColumn(name = "soul_id"))
    @Column(name = "keyword")
    @Size(max = 15)
    private List<String> keywords;

    /** 제작자·설명 */
    @Column(length = 255) private String creator;
    @Column(columnDefinition = "TEXT") private String description;
}
