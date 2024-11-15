package com.cricketpulse.app.repository;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/14/2024
 */
import com.cricketpulse.app.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByCoachBooking_Coach_IdAndCoachBooking_Date(Long coachId, LocalDate date);

}
