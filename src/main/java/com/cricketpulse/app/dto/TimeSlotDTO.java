package com.cricketpulse.app.dto;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/14/2024
 */

import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
}