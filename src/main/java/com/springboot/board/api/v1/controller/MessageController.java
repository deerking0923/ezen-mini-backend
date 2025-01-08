package com.springboot.board.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.springboot.board.api.v1.dto.request.MessageCreateRequest;
import com.springboot.board.api.v1.dto.response.MessageResponse;
import com.springboot.board.application.service.MessageService;
import com.springboot.board.common.response.ApiResponse;
import com.springboot.board.common.response.ErrorResponse;

import java.util.List;

@Tag(name = "Message", description = "채팅 메시지 관련 API")
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "메시지 생성", description = "새로운 메시지를 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse( // FQN 사용
                    responseCode = "201", description = "메시지 생성 성공", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse( // FQN 사용
                    responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MessageResponse> createMessage(
            @RequestBody @Valid MessageCreateRequest request) {
        return ApiResponse.success(messageService.createMessage(request));
    }

    @Operation(summary = "메시지 목록 조회", description = "모든 메시지를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse( // FQN 사용
                    responseCode = "200", description = "메시지 목록 조회 성공", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse( // FQN 사용
                    responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ApiResponse<List<MessageResponse>> getAllMessages() {
        return ApiResponse.success(messageService.getAllMessages());
    }
}
