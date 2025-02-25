package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YurangSoulResponse {
    private String seasonName;
    private String yurangName;
    private String url;
    private int rerunCount;
}
