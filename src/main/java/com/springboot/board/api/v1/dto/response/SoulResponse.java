package com.springboot.board.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

@Getter
@Builder
public class SoulResponse {
    private Integer id;
    private String seasonName;
    private String representativeImage;
    private String name;
    private Integer orderNum;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rerunCount;
    private String locationImage;
    private String nodeTableImage;
    private List<String> gestureGifs;
    private List<String> wearingShotImages;
    private List<String> keywords;
    private List<SoulNodeResponse> centerNodes;
    private List<SoulNodeResponse> leftSideNodes;
    private List<SoulNodeResponse> rightSideNodes;
    
    //  
    private String creator;
    private String description;
    private String materialUrl;
}
