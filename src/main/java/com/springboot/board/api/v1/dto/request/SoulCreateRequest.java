package com.springboot.board.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SoulCreateRequest {

    @NotBlank(message = "시즌 이름은 필수 입력값입니다.")
    @Size(max = 255)
    private String seasonName;

    @Size(max = 255)
    private String representativeImage;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 255)
    private String name;
    
    // 추가: 순서 (몇번째 영혼인지)
    @NotNull(message = "순서는 필수 입력값입니다.")
    private Integer orderNum;

    @NotNull(message = "시작 날짜는 필수 입력값입니다.")
    private LocalDate startDate;

    @NotNull(message = "마감 날짜는 필수 입력값입니다.")
    private LocalDate endDate;

    private int rerunCount;

    @Size(max = 255)
    private String locationImage;

    @Size(max = 255)
    private String NodeTableImage;

    private List<String> gestureGifs;

    private List<String> wearingShotImages;

    private List<String> keywords;
    
    // 추가 필드
    @Size(max = 255)
    private String creator;
    
    private String description;
    
    // 노드 관련 필드
    private List<SoulNodeRequest> centerNodes;
    private List<SoulNodeRequest> leftSideNodes;
    private List<SoulNodeRequest> rightSideNodes;
}
