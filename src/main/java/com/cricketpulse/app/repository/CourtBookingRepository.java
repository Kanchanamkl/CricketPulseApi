package com.cricketpulse.app.repository;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/15/2024
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cricketpulse.app.entity.CourtBooking;

import java.util.List;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBooking, Long> {
    List<CourtBooking> findAllByMemberId(Long memberId);
}