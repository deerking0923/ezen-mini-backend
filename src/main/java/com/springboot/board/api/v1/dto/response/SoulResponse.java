package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class SoulResponse {
    private Integer id;
    private String seasonName;
    private String name;
    private Integer orderNum;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rerunCount;
    private List<String> keywords;
    private String creator;
    private String description;
    private List<ImageResponse> images; // ✅ 추가
}
