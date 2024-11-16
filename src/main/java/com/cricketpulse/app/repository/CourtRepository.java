package com.cricketpulse.app.repository;

import com.cricketpulse.app.entity.Court;
import org.springframework.stereotype.Repository;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/15/2024
 */
import com.cricketpulse.app.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
}
