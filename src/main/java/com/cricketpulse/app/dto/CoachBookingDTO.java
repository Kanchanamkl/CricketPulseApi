package com.cricketpulse.app.dto;

import com.cricketpulse.app.entity.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author : Kanchana Kalansooriya
 * @since 11/13/2024
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CoachBookingDTO {

    private Long id;
    private Long coachId;
    private Long memberId;
    private LocalDate date;
    private int slotCount;
    private List<TimeSlot> timeSlots;
}

