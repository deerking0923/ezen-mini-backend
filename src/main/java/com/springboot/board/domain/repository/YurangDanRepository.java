package com.springboot.board.domain.repository;

import com.springboot.board.domain.entity.YurangDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YurangDanRepository extends JpaRepository<YurangDan, Long> {
}
