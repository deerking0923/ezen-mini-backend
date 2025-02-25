package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class YurangDanResponse {
    private Long id;
    private int roundNumber;
    private String representativeImage;
    private String locationImage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String sourceUrl;
    private List<String> keywords;
    private String materialUrl;
    private List<YurangSoulResponse> yurangSouls;
}
