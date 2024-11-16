package com.cricketpulse.app.repository;

import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/10/2024
 */
@Repository
public interface CoachRepository  extends JpaRepository<Coach, Long> {
  Optional<Coach> findByUser(User user);
}

