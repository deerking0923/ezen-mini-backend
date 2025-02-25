package com.springboot.board.api.v1.controller;

import com.springboot.board.api.v1.dto.request.YurangDanCreateRequest;
import com.springboot.board.api.v1.dto.request.YurangDanUpdateRequest;
import com.springboot.board.api.v1.dto.response.YurangDanResponse;
import com.springboot.board.application.service.YurangDanService;
import com.springboot.board.common.response.ApiResponse;
import com.springboot.board.common.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "YurangDan", description = "유랑단 관련 API")
@RestController
@RequestMapping("/api/v1/yurangdans")
@RequiredArgsConstructor
public class YurangDanController {

    private final YurangDanService yurangDanService;

    @Operation(summary = "유랑단 목록 조회", description = "유랑단 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = YurangDanResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ApiResponse<List<YurangDanResponse>> getAllYurangDans() {
        return ApiResponse.success(yurangDanService.getAllYurangDans());
    }

    @Operation(summary = "유랑단 생성", description = "새로운 유랑단을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = YurangDanResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<YurangDanResponse> createYurangDan(
            @Valid @RequestBody YurangDanCreateRequest request) {
        return ApiResponse.success(yurangDanService.createYurangDan(request));
    }

    @Operation(summary = "유랑단 상세 조회", description = "특정 id의 유랑단을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = YurangDanResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "유랑단 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ApiResponse<YurangDanResponse> getYurangDan(@PathVariable Long id) {
        return ApiResponse.success(yurangDanService.getYurangDan(id));
    }

    @Operation(summary = "유랑단 수정", description = "특정 id의 유랑단을 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = YurangDanResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "유랑단 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ApiResponse<YurangDanResponse> updateYurangDan(
            @PathVariable Long id,
            @Valid @RequestBody YurangDanUpdateRequest request) {
        return ApiResponse.success(yurangDanService.updateYurangDan(id, request));
    }

    @Operation(summary = "유랑단 삭제", description = "특정 id의 유랑단을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "유랑단 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteYurangDan(@PathVariable Long id) {
        yurangDanService.deleteYurangDan(id);
    }
}
