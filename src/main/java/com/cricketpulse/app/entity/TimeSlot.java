package com.cricketpulse.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

/**
 * Author: Kanchana Kalansooriya
 * Since: 11/13/2024
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TIME_SLOT")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_booking_id", nullable = false)
    private CoachBooking coachBooking;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;
}