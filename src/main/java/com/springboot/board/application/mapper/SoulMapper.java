package com.springboot.board.application.mapper;

import com.springboot.board.api.v1.dto.request.SoulCreateRequest;
import com.springboot.board.api.v1.dto.request.SoulUpdateRequest;
import com.springboot.board.api.v1.dto.response.SoulResponse;
import com.springboot.board.domain.entity.SoulEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SoulMapper {

    /** 생성 요청 → 엔티티 */
    SoulEntity toEntity(SoulCreateRequest request);

    /** 엔티티 → 응답 DTO */
    SoulResponse toResponse(SoulEntity entity);

    /** 업데이트: DTO → 기존 엔티티 필드 덮어쓰기 */
    void updateEntity(@MappingTarget SoulEntity entity, SoulUpdateRequest request);
}
