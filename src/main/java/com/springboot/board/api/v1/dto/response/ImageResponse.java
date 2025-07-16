package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponse {
    private Long   id;         // ⚡️추가 – 삭제용 PK
    private String imageType;  // REPRESENTATIVE · LOCATION · …
    private String url;        // 정적 URL
}
