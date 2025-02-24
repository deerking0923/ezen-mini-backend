package com.springboot.board.domain.repository;

import com.springboot.board.domain.entity.SoulEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoulRepository extends JpaRepository<SoulEntity, Integer> {
}
