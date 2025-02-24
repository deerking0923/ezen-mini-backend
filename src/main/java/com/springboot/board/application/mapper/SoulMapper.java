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

    // 생성 요청 → 엔티티 변환
    SoulEntity toEntity(SoulCreateRequest request);

    // 업데이트 요청 → 엔티티 변환
    SoulEntity toEntity(SoulUpdateRequest request);

    // 엔티티 → 응답 DTO 변환
    SoulResponse toResponse(SoulEntity soulEntity);

    // 노드 요청 DTO → 노드 엔티티 변환
    SoulNode toSoulNode(SoulNodeRequest request);

    // 노드 엔티티 → 노드 응답 DTO 변환
    SoulNodeResponse toSoulNodeResponse(SoulNode soulNode);

    // 리스트 변환
    List<SoulNodeResponse> toSoulNodeResponses(List<SoulNode> soulNodes);
}
