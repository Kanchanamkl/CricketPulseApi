package com.cricketpulse.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtBookingDTO {

    private Long id;
    private Long courtId;
    private Long memberId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

}