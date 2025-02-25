package com.springboot.board.application.service;

import com.springboot.board.api.v1.dto.request.YurangDanCreateRequest;
import com.springboot.board.api.v1.dto.request.YurangDanUpdateRequest;
import com.springboot.board.api.v1.dto.response.YurangDanResponse;
import com.springboot.board.application.mapper.YurangDanMapper;
import com.springboot.board.domain.entity.YurangDan;
import com.springboot.board.domain.repository.YurangDanRepository;
import com.springboot.board.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YurangDanService {

    private final YurangDanRepository repository;
    private final YurangDanMapper mapper;

    @Transactional
    public YurangDanResponse createYurangDan(YurangDanCreateRequest request) {
        YurangDan entity = mapper.toEntity(request);
        YurangDan saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    public YurangDanResponse getYurangDan(Long id) {
        YurangDan entity = repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("유랑단을 찾을 수 없습니다. id: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public YurangDanResponse updateYurangDan(Long id, YurangDanUpdateRequest request) {
        YurangDan entity = repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("유랑단을 찾을 수 없습니다. id: " + id));

        entity.setRoundNumber(request.getRoundNumber());
        entity.setRepresentativeImage(request.getRepresentativeImage());
        entity.setLocationImage(request.getLocationImage());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setSourceUrl(request.getSourceUrl());
        entity.setKeywords(request.getKeywords());
        entity.setMaterialUrl(request.getMaterialUrl());
        entity.setYurangSouls(request.getYurangSouls() != null
                ? request.getYurangSouls().stream().map(mapper::toYurangSoul).toList()
                : null);

        YurangDan updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void deleteYurangDan(Long id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException("유랑단을 찾을 수 없습니다. id: " + id);
        }
        repository.deleteById(id);
    }

    public List<YurangDanResponse> getAllYurangDans() {
        List<YurangDan> list = repository.findAll();
        return list.stream().map(mapper::toResponse).toList();
    }
}
