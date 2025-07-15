// src/main/java/com/springboot/board/domain/repository/ImageRepository.java
package com.springboot.board.domain.repository;

import com.springboot.board.domain.entity.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    /** 영혼별 필터용(선택) */
    Page<ImageEntity> findAllBySoulId(Integer soulId, Pageable pageable);
}
