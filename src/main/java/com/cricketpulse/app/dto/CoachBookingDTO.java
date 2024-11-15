package com.cricketpulse.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


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
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

}

