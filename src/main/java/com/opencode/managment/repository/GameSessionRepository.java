package com.opencode.managment.repository;

import com.opencode.managment.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepository extends JpaRepository<GameSession, Integer> {
}
