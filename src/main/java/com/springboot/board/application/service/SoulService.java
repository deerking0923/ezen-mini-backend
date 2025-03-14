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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                Pageable pageable = PageRequest.of(page, 15, Sort.by(
                                Sort.Order.desc("startDate"),
                                Sort.Order.desc("name")));
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
                soulEntity.setOrderNum(request.getOrderNum());
                soulEntity.setStartDate(request.getStartDate());
                soulEntity.setEndDate(request.getEndDate());
                soulEntity.setRerunCount(request.getRerunCount());
                soulEntity.setLocationImage(request.getLocationImage());
                soulEntity.setNodeTableImage(request.getNodeTableImage());
                soulEntity.setGestureGifs(request.getGestureGifs());
                soulEntity.setWearingShotImages(request.getWearingShotImages());
                soulEntity.setKeywords(request.getKeywords());
                soulEntity.setCreator(request.getCreator());
                soulEntity.setDescription(request.getDescription());
                soulEntity.setMaterialUrl(request.getMaterialUrl());

                soulEntity.setCenterNodes(
                                request.getCenterNodes() != null
                                                ? request.getCenterNodes().stream()
                                                                .map(soulMapper::toSoulNode)
                                                                .collect(Collectors.toCollection(ArrayList::new))
                                                : null);
                soulEntity.setLeftSideNodes(
                                request.getLeftSideNodes() != null
                                                ? request.getLeftSideNodes().stream()
                                                                .map(soulMapper::toSoulNode)
                                                                .collect(Collectors.toCollection(ArrayList::new))
                                                : null);
                soulEntity.setRightSideNodes(
                                request.getRightSideNodes() != null
                                                ? request.getRightSideNodes().stream()
                                                                .map(soulMapper::toSoulNode)
                                                                .collect(Collectors.toCollection(ArrayList::new))
                                                : null);

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

        // 검색 기능 추가
        public List<SoulResponse> searchSouls(String query) {
                List<SoulEntity> souls = soulRepository.searchSouls(query);
                return souls.stream()
                                .map(soulMapper::toResponse)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<SoulResponse> getAllSouls() {
                List<SoulEntity> souls = soulRepository.findAll(Sort.by(
                                Sort.Order.desc("startDate"),
                                Sort.Order.desc("name")));
                return souls.stream()
                                .map(soulMapper::toResponse)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<SoulResponse> getAllSoulsReversed() {
                // 내림차순 정렬의 반대로 오름차순으로 정렬
                List<SoulEntity> souls = soulRepository.findAll(Sort.by(
                                Sort.Order.asc("startDate"),
                                Sort.Order.asc("name")));
                return souls.stream()
                                .map(soulMapper::toResponse)
                                .collect(Collectors.toList());
        }

        // SoulService.java (필요한 import 추가: java.util.Map, java.util.HashMap,
        // java.util.ArrayList, java.util.Collections)
        @Transactional(readOnly = true)
        public Map<String, List<SoulResponse>> getNeighbors(Integer id) {
                // 현재 영혼 노드 조회
                SoulEntity currentSoul = soulRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("영혼 노드를 찾을 수 없습니다. id: " + id));

                // 동일 정렬 기준(시작일 내림, 이름 내림)으로 전체 영혼 목록 조회
                List<SoulEntity> allSouls = soulRepository.findAll(Sort.by(
                                Sort.Order.desc("startDate"),
                                Sort.Order.desc("name")));

                // 현재 노드의 인덱스 찾기
                int index = -1;
                for (int i = 0; i < allSouls.size(); i++) {
                        if (allSouls.get(i).getId().equals(id)) {
                                index = i;
                                break;
                        }
                }
                if (index == -1) {
                        throw new DataNotFoundException("영혼 노드를 찾을 수 없습니다. id: " + id);
                }

                List<SoulResponse> prevList = new ArrayList<>();
                List<SoulResponse> nextList = new ArrayList<>();

                // 이전글 최대 2개 (현재보다 앞쪽)
                int startPrev = Math.max(0, index - 2);
                for (int i = index - 1; i >= startPrev; i--) {
                        prevList.add(soulMapper.toResponse(allSouls.get(i)));
                }
                Collections.reverse(prevList); // 올바른 순서로 정렬

                // 다음글 최대 2개 (현재보다 뒤쪽)
                int endNext = Math.min(allSouls.size() - 1, index + 2);
                for (int i = index + 1; i <= endNext; i++) {
                        nextList.add(soulMapper.toResponse(allSouls.get(i)));
                }

                Map<String, List<SoulResponse>> neighbors = new HashMap<>();
                neighbors.put("prev", prevList);
                neighbors.put("next", nextList);

                return neighbors;
        }

}
