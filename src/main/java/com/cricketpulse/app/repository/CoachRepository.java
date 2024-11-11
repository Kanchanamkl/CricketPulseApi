package com.cricketpulse.app.repository;

import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/10/2024
 */
public interface CoachRepository  extends JpaRepository<Coach, Long> {
  Optional<Coach> findByUser(User user);
}

