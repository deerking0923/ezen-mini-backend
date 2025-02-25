package com.springboot.board.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YurangSoul {
    // 시즌 이름
    @Column(length = 255, nullable = false)
    private String seasonName;

    // 유랑 이름
    @Column(length = 255, nullable = false)
    private String yurangName;

    // URL 주소
    @Column(length = 255, nullable = true)
    private String url;

    // 각각 몇차 복각인지
    private int rerunCount;
}
