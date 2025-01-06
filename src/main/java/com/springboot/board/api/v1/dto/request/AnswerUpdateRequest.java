package com.springboot.board.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerUpdateRequest {
    @NotBlank(message = "내용은 반드시 입력하셔야 합니다.")
    private String content;
    private Integer questionId;
}
