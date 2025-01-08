package com.springboot.board.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCreateRequest {

    @NotBlank(message = "사용자 이름은 필수입니다.")
    @Size(max = 50, message = "사용자 이름은 최대 50자까지 가능합니다.")
    private String username;

    @NotBlank(message = "메시지 내용은 필수입니다.")
    @Size(max = 500, message = "메시지 내용은 최대 500자까지 가능합니다.")
    private String content;
}
