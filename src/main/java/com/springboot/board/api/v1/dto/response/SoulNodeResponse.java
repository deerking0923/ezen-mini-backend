package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
public class SoulNodeResponse {
    private Integer nodeOrder;
    private String photo;
    private BigDecimal currencyPrice;
}
