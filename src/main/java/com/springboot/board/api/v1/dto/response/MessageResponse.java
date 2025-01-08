package com.springboot.board.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String username;
    private String content;
    private LocalDateTime timestamp;
}
