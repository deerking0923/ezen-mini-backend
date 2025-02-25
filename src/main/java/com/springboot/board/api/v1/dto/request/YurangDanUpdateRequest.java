package com.springboot.board.api.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class YurangDanUpdateRequest {
    @NotNull(message = "몇차인지 필수 입력값입니다.")
    private int roundNumber;

    @Size(max = 255)
    private String representativeImage;

    @Size(max = 255)
    private String locationImage;

    @NotNull(message = "시작 날짜는 필수 입력값입니다.")
    private LocalDate startDate;

    @NotNull(message = "끝난 날짜는 필수 입력값입니다.")
    private LocalDate endDate;

    @Size(max = 255)
    private String sourceUrl;

    @Size(max = 15, message = "최대 15개의 키워드만 가능합니다.")
    private List<String> keywords;


    private List<YurangSoulRequest> yurangSouls;
}
