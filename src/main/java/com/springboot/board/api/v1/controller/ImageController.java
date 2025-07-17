package com.springboot.board.api.v1.controller;

import com.springboot.board.api.v1.dto.response.ImageResponse;
import com.springboot.board.application.service.ImageService;
import com.springboot.board.common.response.ApiResponse;
import com.springboot.board.domain.entity.ImageEntity;
import com.springboot.board.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    /** 1) 업로드 **/
    @PostMapping
    public ApiResponse<ImageResponse> upload(
            @RequestParam(required = false) Integer soulId,
            @RequestParam String imageType,
            @RequestParam MultipartFile file) throws Exception {

        ImageEntity img = imageService.upload(soulId, imageType, file);
        return ApiResponse.success(toDto(img));
    }

    /** 2) 교체 **/
    @PutMapping("/{id}")
    public ApiResponse<ImageResponse> replace(
            @PathVariable Long id,
            @RequestParam MultipartFile file) throws Exception {

        ImageEntity img = imageService.replace(id, file);
        return ApiResponse.success(toDto(img));
    }

    /** 3) 삭제 **/
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) throws Exception {
        imageService.delete(id);
        return ApiResponse.success(null);
    }

    /** 4) 한 개만 조회 **/
    @GetMapping("/{id}")
    public ApiResponse<ImageResponse> getOne(@PathVariable Long id) {
        ImageEntity img = imageRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Image not found. id=" + id));
        return ApiResponse.success(toDto(img));
    }

    /** 5) 리스트(페이징 + 선택적 soulId 필터) **/
    @GetMapping
    public ApiResponse<Page<ImageResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Integer soulId) {

        var pr = PageRequest.of(page, size);
        Page<ImageEntity> entities = (soulId == null)
            ? imageRepository.findAll(pr)
            : imageRepository.findAllBySoulId(soulId, pr);

        Page<ImageResponse> dtos = entities.map(this::toDto);
        return ApiResponse.success(dtos);
    }

    /** 공통 DTO 변환 메서드 **/
    private ImageResponse toDto(ImageEntity img) {
        // 현재 서버의 호스트+포트를 붙여서 절대경로 생성
        String absoluteUrl = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path(img.getUrl())
            .toUriString();

        return ImageResponse.builder()
                .id(img.getId())
                .soulId(img.getSoul() != null ? img.getSoul().getId() : null)
                .imageType(img.getImageType())
                .url(absoluteUrl)       // <-- 절대 URL
                .fileName(img.getFileName())
                .fileSize(img.getFileSize())
                .uploadedAt(img.getUploadedAt())
                .build();
    }
}
