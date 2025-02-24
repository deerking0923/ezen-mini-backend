package com.springboot.board.api.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UploadController {

    // 파일을 저장할 디렉토리 (프로젝트 루트 기준)
    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            // 업로드 폴더가 없으면 생성
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 원본 파일 이름에서 확장자 추출 (없으면 빈 문자열)
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // UUID를 이용하여 고유 파일명 생성
            String uniqueFileName = UUID.randomUUID().toString() + extension;
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 업로드된 파일 URL 생성 (정적 리소스 제공 설정 필요)
            String fileUrl = "https://korea-sky-planner.com/uploads/" + uniqueFileName;
            response.put("url", fileUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException ex) {
            response.put("error", "파일 업로드에 실패하였습니다: " + ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
