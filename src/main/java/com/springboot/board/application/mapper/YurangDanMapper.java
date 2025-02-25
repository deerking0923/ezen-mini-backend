package com.springboot.board.application.mapper;

import org.mapstruct.Mapper;
import java.util.List;
import com.springboot.board.api.v1.dto.request.YurangDanCreateRequest;
import com.springboot.board.api.v1.dto.request.YurangDanUpdateRequest;
import com.springboot.board.api.v1.dto.request.YurangSoulRequest;
import com.springboot.board.api.v1.dto.response.YurangDanResponse;
import com.springboot.board.api.v1.dto.response.YurangSoulResponse;
import com.springboot.board.domain.entity.YurangDan;
import com.springboot.board.domain.entity.YurangSoul;

@Mapper(componentModel = "spring")
public interface YurangDanMapper {
    YurangDan toEntity(YurangDanCreateRequest request);
    YurangDan toEntity(YurangDanUpdateRequest request);
    YurangDanResponse toResponse(YurangDan entity);
    YurangSoul toYurangSoul(YurangSoulRequest request);
    YurangSoulResponse toYurangSoulResponse(YurangSoul soul);
    List<YurangSoulResponse> toYurangSoulResponses(List<YurangSoul> souls);
}
