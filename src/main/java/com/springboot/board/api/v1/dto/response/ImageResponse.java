package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponse {
    private String imageType;
    private String url;
}
