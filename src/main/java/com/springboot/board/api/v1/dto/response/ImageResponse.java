package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ImageResponse {
    private Long id;
    private Integer soulId;
    private String imageType;
    private String url;
    private String fileName;
    private long fileSize;
    private LocalDateTime uploadedAt;
}
