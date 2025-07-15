// src/main/java/com/springboot/board/api/v1/controller/ImageController.java
package com.springboot.board.api.v1.controller;

import com.springboot.board.application.service.ImageService;
import com.springboot.board.common.response.ApiResponse;
import com.springboot.board.domain.entity.ImageEntity;
import com.springboot.board.domain.repository.ImageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    /** 업로드 */
    @PostMapping
    public ApiResponse<ImageEntity> upload(
            @RequestParam(required = false) Integer soulId,
            @RequestParam String imageType,
            @RequestParam MultipartFile file) throws Exception {
        return ApiResponse.success(imageService.upload(soulId, imageType, file));
    }

    /** 교체 */
    @PutMapping("/{id}")
    public ApiResponse<ImageEntity> replace(
            @PathVariable Long id,
            @RequestParam MultipartFile file) throws Exception {
        return ApiResponse.success(imageService.replace(id, file));
    }

    /** 삭제 */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        imageService.delete(id);
    }

    /** 전체/페이징/필터 목록 */
    @GetMapping
    public ApiResponse<Page<ImageEntity>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Integer soulId) {

        var pageable = PageRequest.of(page, 20);
        Page<ImageEntity> result = (soulId == null)
                ? imageRepository.findAll(pageable)
                : imageRepository.findAllBySoulId(soulId, pageable);
        return ApiResponse.success(result);
    }
}
