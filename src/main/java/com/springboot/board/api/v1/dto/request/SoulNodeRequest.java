package com.springboot.board.api.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class SoulNodeRequest {

    @NotNull(message = "노드 순서는 필수입니다.")
    private Integer nodeOrder;

    @NotBlank(message = "노드 사진은 필수입니다.")
    @Size(max = 255)
    private String photo;

    @NotNull(message = "재화 가격은 필수입니다.")
    private BigDecimal currencyPrice;
}
