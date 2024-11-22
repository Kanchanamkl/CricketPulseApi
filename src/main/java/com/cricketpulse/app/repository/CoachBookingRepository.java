package com.cricketpulse.app.repository;

import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachBookingRepository extends JpaRepository<CoachBooking, Long> {

    List<CoachBooking> findAllByMemberId(Long memberId);
}