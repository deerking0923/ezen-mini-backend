package com.springboot.board.application.service;

import com.springboot.board.application.mapper.SoulMapper;
import com.springboot.board.api.v1.dto.request.SoulCreateRequest;
import com.springboot.board.api.v1.dto.request.SoulUpdateRequest;
import com.springboot.board.api.v1.dto.response.SoulResponse;
import com.springboot.board.domain.entity.SoulEntity;
import com.springboot.board.domain.repository.SoulRepository;
import com.springboot.board.common.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SoulService {

    private final SoulRepository soulRepository;
    private final SoulMapper soulMapper;

    @Transactional
    public SoulResponse createSoul(SoulCreateRequest request) {
        SoulEntity soulEntity = soulMapper.toEntity(request);
        SoulEntity savedSoul = soulRepository.save(soulEntity);
        return soulMapper.toResponse(savedSoul);
    }

    public Page<SoulResponse> getSouls(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<SoulEntity> souls = soulRepository.findAll(pageable);
        return souls.map(soulMapper::toResponse);
    }

    public SoulResponse getSoul(Integer id) {
        SoulEntity soul = soulRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("영혼 노드를 찾을 수 없습니다. id: " + id));
        return soulMapper.toResponse(soul);
    }

    @Transactional
    public SoulResponse updateSoul(Integer id, SoulUpdateRequest request) {
        SoulEntity soulEntity = soulRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("영혼 노드를 찾을 수 없습니다. id: " + id));

        soulEntity.setSeasonName(request.getSeasonName());
        soulEntity.setRepresentativeImage(request.getRepresentativeImage());
        soulEntity.setName(request.getName());
        soulEntity.setStartDate(request.getStartDate());
        soulEntity.setEndDate(request.getEndDate());
        soulEntity.setRerunCount(request.getRerunCount());
        soulEntity.setLocationImage(request.getLocationImage());
        soulEntity.setGestureGifs(request.getGestureGifs());
        soulEntity.setWearingShotImages(request.getWearingShotImages());
        soulEntity.setKeywords(request.getKeywords());
        soulEntity.setCenterNodes(
                request.getCenterNodes() != null ? request.getCenterNodes().stream()
                       .map(soulMapper::toSoulNode)
                       .toList() : null
        );
        soulEntity.setLeftSideNodes(
                request.getLeftSideNodes() != null ? request.getLeftSideNodes().stream()
                       .map(soulMapper::toSoulNode)
                       .toList() : null
        );
        soulEntity.setRightSideNodes(
                request.getRightSideNodes() != null ? request.getRightSideNodes().stream()
                       .map(soulMapper::toSoulNode)
                       .toList() : null
        );

        SoulEntity updatedSoul = soulRepository.save(soulEntity);
        return soulMapper.toResponse(updatedSoul);
    }

    @Transactional
    public void deleteSoul(Integer id) {
        if (!soulRepository.existsById(id)) {
            throw new DataNotFoundException("영혼 노드를 찾을 수 없습니다. id: " + id);
        }
        soulRepository.deleteById(id);
    }
}
