// src/main/java/com/springboot/board/application/service/ImageService.java
package com.springboot.board.application.service;

import com.springboot.board.common.exception.DataNotFoundException;
import com.springboot.board.domain.entity.ImageEntity;
import com.springboot.board.domain.entity.SoulEntity;
import com.springboot.board.domain.repository.ImageRepository;
import com.springboot.board.domain.repository.SoulRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    /** 업로드할 디렉토리 (application.yml 로 분리해도 됩니다) */
    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    /** 호스트 절대경로 (application.yml 에 설정) */
    @Value("${app.base-url}")
    private String baseUrl;

    /** 업로드 */
    @Transactional
    public ImageEntity upload(Integer soulId, String imageType, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String ext = getExtension(file.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Path target = uploadPath.resolve(uniqueName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        ImageEntity entity = ImageEntity.builder()
                .imageType(imageType)
                .fileName(uniqueName)
                // 절대경로로 저장
                .url(baseUrl + "/" + uploadDir + "/" + uniqueName)
                .fileSize(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .build();

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
        Path uploadPath = Paths.get(uploadDir);

        ImageEntity existing = imageRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("이미지를 찾을 수 없습니다. id=" + id));

        // 기존 파일 삭제
        Files.deleteIfExists(uploadPath.resolve(existing.getFileName()));

        // 새 파일 저장
        String ext = getExtension(newFile.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Path target = uploadPath.resolve(uniqueName);
        Files.copy(newFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        existing.setFileName(uniqueName);
        existing.setUrl(baseUrl + "/" + uploadDir + "/" + uniqueName);
        existing.setFileSize(newFile.getSize());
        existing.setUploadedAt(LocalDateTime.now());
        return existing;
    }

    /** 삭제 */
    @Transactional
    public void delete(Long id) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        ImageEntity img = imageRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("이미지를 찾을 수 없습니다. id=" + id));
        Files.deleteIfExists(uploadPath.resolve(img.getFileName()));
        imageRepository.delete(img);
    }

    private String getExtension(String original) {
        int dot = (original == null) ? -1 : original.lastIndexOf('.');
        return (dot == -1) ? "" : original.substring(dot);
    }
}
