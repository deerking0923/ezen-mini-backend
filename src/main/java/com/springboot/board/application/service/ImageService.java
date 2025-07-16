// src/main/java/com/springboot/board/application/service/ImageService.java
package com.springboot.board.application.service;

import com.springboot.board.common.exception.DataNotFoundException;
import com.springboot.board.domain.entity.ImageEntity;
import com.springboot.board.domain.entity.SoulEntity;
import com.springboot.board.domain.repository.ImageRepository;
import com.springboot.board.domain.repository.SoulRepository;
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
    private final SoulRepository soulRepository;

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
                .imageType(imageType)
                .fileName(uniqueName)
                .url("/uploads/" + uniqueName)
                .fileSize(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .build();

        // soulId가 넘어오면 연관관계 연결
        if (soulId != null) {
            SoulEntity soul = soulRepository.findById(soulId)
                    .orElseThrow(() -> new DataNotFoundException("영혼이 존재하지 않습니다. id=" + soulId));
            entity.setSoul(soul);
        }

        return imageRepository.save(entity);
    }

    /** 교체(수정) */
    @Transactional
    public ImageEntity replace(Long id, MultipartFile newFile) throws IOException {
        ImageEntity existing = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("image not found"));

        Files.deleteIfExists(UPLOAD_DIR.resolve(existing.getFileName()));

        String ext = getExtension(newFile.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Files.copy(newFile.getInputStream(), UPLOAD_DIR.resolve(uniqueName),
                StandardCopyOption.REPLACE_EXISTING);

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
