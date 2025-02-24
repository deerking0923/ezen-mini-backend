package com.springboot.board.api.v1.controller;

import com.springboot.board.api.v1.dto.request.SoulCreateRequest;
import com.springboot.board.api.v1.dto.request.SoulUpdateRequest;
import com.springboot.board.api.v1.dto.response.SoulResponse;
import com.springboot.board.application.service.SoulService;
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
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

@Tag(name = "Soul", description = "영혼 노드표 관련 API")
@RestController
@RequestMapping("/api/v1/souls")
@RequiredArgsConstructor
public class SoulController {

    private final SoulService soulService;

    @Operation(summary = "영혼 목록 조회", description = "페이지네이션된 영혼 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SoulResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ApiResponse<Page<SoulResponse>> getSouls(
            @RequestParam(defaultValue = "0") int page) {
        return ApiResponse.success(soulService.getSouls(page));
    }

    @Operation(summary = "영혼 생성", description = "새로운 영혼 노드를 생성합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = SoulResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SoulResponse> createSoul(
            @Valid @RequestBody SoulCreateRequest request) {
        return ApiResponse.success(soulService.createSoul(request));
    }

    @Operation(summary = "영혼 상세 조회", description = "특정 ID의 영혼 노드를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SoulResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "영혼 노드 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ApiResponse<SoulResponse> getSoul(@PathVariable Integer id) {
        return ApiResponse.success(soulService.getSoul(id));
    }

    @Operation(summary = "영혼 수정", description = "특정 ID의 영혼 노드를 수정합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = SoulResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "영혼 노드 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ApiResponse<SoulResponse> updateSoul(
            @PathVariable Integer id,
            @Valid @RequestBody SoulUpdateRequest request) {
        return ApiResponse.success(soulService.updateSoul(id, request));
    }

    @Operation(summary = "영혼 삭제", description = "특정 ID의 영혼 노드를 삭제합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "영혼 노드 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSoul(@PathVariable Integer id) {
        soulService.deleteSoul(id);
    }
}
