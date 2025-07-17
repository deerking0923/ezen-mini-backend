// src/main/java/com/springboot/board/application/service/ImageService.java
package com.springboot.board.application.service;

import com.springboot.board.common.exception.DataNotFoundException;
import com.springboot.board.domain.entity.ImageEntity;
import com.springboot.board.domain.entity.SoulEntity;
import com.springboot.board.domain.repository.ImageRepository;
import com.springboot.board.domain.repository.SoulRepository;
import jakarta.annotation.PostConstruct;
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

    /** application.yml 에서 주입 **/
    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:https://localhost:8080}")
    private String baseUrl;

    private Path uploadPath;

    /** uploadDir 프로퍼티 기반으로 실제 Path 객체 생성 */
    @PostConstruct
    public void init() throws IOException {
        uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    /** 업로드 */
    @Transactional
    public ImageEntity upload(Integer soulId, String imageType, MultipartFile file) throws IOException {
        // 파일명 생성
        String ext = getExtension(file.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Path target = uploadPath.resolve(uniqueName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // DB에 저장할 엔티티 준비
        ImageEntity entity = ImageEntity.builder()
                .imageType(imageType)
                .fileName(uniqueName)
                // 클라이언트가 그대로 사용 가능한 절대 URL
                .url(baseUrl + "/" + uploadDir + "/" + uniqueName)
                .fileSize(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .build();

        // soulId 연관관계 처리
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

        // 이전 파일 삭제
        Files.deleteIfExists(uploadPath.resolve(existing.getFileName()));

        // 새 파일 저장
        String ext = getExtension(newFile.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + ext;
        Path target = uploadPath.resolve(uniqueName);
        Files.copy(newFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // 엔티티 업데이트
        existing.setFileName(uniqueName);
        existing.setUrl(baseUrl + "/" + uploadDir + "/" + uniqueName);
        existing.setFileSize(newFile.getSize());
        existing.setUploadedAt(LocalDateTime.now());
        return existing;
    }

    /** 삭제 */
    @Transactional
    public void delete(Long id) throws IOException {
        ImageEntity img = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("image not found"));
        Files.deleteIfExists(uploadPath.resolve(img.getFileName()));
        imageRepository.delete(img);
    }

    private String getExtension(String original) {
        int dot = original == null ? -1 : original.lastIndexOf('.');
        return (dot == -1) ? "" : original.substring(dot);
    }
}
