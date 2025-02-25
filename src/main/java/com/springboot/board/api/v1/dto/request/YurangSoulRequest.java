package com.springboot.board.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class YurangSoulRequest {
    @NotBlank(message = "시즌 이름은 필수 입력값입니다.")
    @Size(max = 255)
    private String seasonName;

    @NotBlank(message = "유랑 이름은 필수 입력값입니다.")
    @Size(max = 255)
    private String yurangName;

    @NotBlank(message = "URL 주소는 필수 입력값입니다.")
    @Size(max = 255)
    private String url;

    @NotNull(message = "복각 횟수는 필수 입력값입니다.")
    private int rerunCount;
}
