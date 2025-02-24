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
public class SoulUpdateRequest {

    @NotBlank(message = "시즌 이름은 필수 입력값입니다.")
    @Size(max = 255)
    private String seasonName;

    @Size(max = 255)
    private String representativeImage;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 255)
    private String name;

    @NotNull(message = "시작 날짜는 필수 입력값입니다.")
    private LocalDate startDate;

    @NotNull(message = "마감 날짜는 필수 입력값입니다.")
    private LocalDate endDate;

    private int rerunCount;

    @Size(max = 255)
    private String locationImage;

    // 제스처 GIF URL을 여러 개 저장
    private List<@NotBlank String> gestureGifs;

    private List<@NotBlank String> wearingShotImages;
    private List<@NotBlank String> keywords;

    private List<SoulNodeRequest> centerNodes;
    private List<SoulNodeRequest> leftSideNodes;
    private List<SoulNodeRequest> rightSideNodes;
}
