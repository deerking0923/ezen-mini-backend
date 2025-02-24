package com.springboot.board.domain.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoulNode {

    // 노드 순서 (배열 내 위치)
    @Column(name = "node_order")
    private Integer nodeOrder;

    // 노드 사진 URL
    @Column(name = "photo", length = 255)
    private String photo;

    // 재화 가격 (소숫점 포함)
    @Column(name = "currency_price", precision = 10, scale = 2)
    private BigDecimal currencyPrice;
}
