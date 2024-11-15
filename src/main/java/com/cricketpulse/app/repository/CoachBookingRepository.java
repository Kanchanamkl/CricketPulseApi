package com.cricketpulse.app.repository;

import com.cricketpulse.app.entity.CoachBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachBookingRepository extends JpaRepository<CoachBooking, Long> {
}