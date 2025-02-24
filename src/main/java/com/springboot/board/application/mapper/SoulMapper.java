package com.springboot.board.application.mapper;

import org.mapstruct.Mapper;
import java.util.List;
import com.springboot.board.api.v1.dto.request.SoulCreateRequest;
import com.springboot.board.api.v1.dto.request.SoulUpdateRequest;
import com.springboot.board.api.v1.dto.request.SoulNodeRequest;
import com.springboot.board.api.v1.dto.response.SoulResponse;
import com.springboot.board.api.v1.dto.response.SoulNodeResponse;
import com.springboot.board.domain.entity.SoulEntity;
import com.springboot.board.domain.entity.SoulNode;

@Mapper(componentModel = "spring")
public interface SoulMapper {

    // SoulCreateRequest → SoulEntity
    SoulEntity toEntity(SoulCreateRequest request);

    // SoulUpdateRequest → SoulEntity (업데이트용)
    SoulEntity toEntity(SoulUpdateRequest request);

    // SoulEntity → SoulResponse
    SoulResponse toResponse(SoulEntity soulEntity);

    // SoulNodeRequest → SoulNode
    SoulNode toSoulNode(SoulNodeRequest request);

    // SoulNode → SoulNodeResponse
    SoulNodeResponse toSoulNodeResponse(SoulNode soulNode);

    // 리스트 변환
    List<SoulNodeResponse> toSoulNodeResponses(List<SoulNode> soulNodes);
}
