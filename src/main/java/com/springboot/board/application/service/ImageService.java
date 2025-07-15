// src/main/java/com/springboot/board/application/service/ImageService.java
package com.springboot.board.application.service;

import com.springboot.board.domain.entity.ImageEntity;
import com.springboot.board.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    /** 저장 경로 (application.properties 로 빼도 됨) */
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    /** 업로드 */
    @Transactional
    public ImageEntity upload(Integer soulId, String imageType, MultipartFile file) throws IOException {

        if (!Files.exists(UPLOAD_DIR)) Files.createDirectories(UPLOAD_DIR);

        String ext = getExtension(file.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Path target = UPLOAD_DIR.resolve(uniqueName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        ImageEntity entity = ImageEntity.builder()
                .soulId(soulId)
                .imageType(imageType)
                .fileName(uniqueName)
                .url("/uploads/" + uniqueName)           // 정적 매핑 그대로 사용
                .fileSize(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .build();

        return imageRepository.save(entity);
    }

    /** 교체(수정) */
    @Transactional
    public ImageEntity replace(Long id, MultipartFile newFile) throws IOException {
        ImageEntity existing = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("image not found"));

        // 1) 기존 파일 삭제
        Files.deleteIfExists(UPLOAD_DIR.resolve(existing.getFileName()));

        // 2) 새 파일 저장
        String ext = getExtension(newFile.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Files.copy(newFile.getInputStream(), UPLOAD_DIR.resolve(uniqueName),
                   StandardCopyOption.REPLACE_EXISTING);

        // 3) 메타데이터 업데이트
        existing.setFileName(uniqueName);
        existing.setUrl("/uploads/" + uniqueName);
        existing.setFileSize(newFile.getSize());
        existing.setUploadedAt(LocalDateTime.now());
        return existing;
    }

    /** 삭제 */
    @Transactional
    public void delete(Long id) throws IOException {
        ImageEntity img = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("image not found"));
        Files.deleteIfExists(UPLOAD_DIR.resolve(img.getFileName()));
        imageRepository.delete(img);
    }

    private String getExtension(String original) {
        int dot = original == null ? -1 : original.lastIndexOf('.');
        return (dot == -1) ? "" : original.substring(dot);
    }
}
