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
    private String representativeImage;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rerunCount;
    private String locationImage;
    // 단일 제스처 GIF에서 다중 제스처 GIF 목록으로 변경
    private List<String> gestureGifs;
    private List<String> wearingShotImages;
    private List<String> keywords;
    private List<SoulNodeResponse> centerNodes;
    private List<SoulNodeResponse> leftSideNodes;
    private List<SoulNodeResponse> rightNodes;
}
