package com.cricketpulse.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
@Table(name = "COACH_BOOKING")
public class CoachBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;
//    private int slotCount;

//    @OneToMany(mappedBy = "coachBooking", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TimeSlot> timeSlots;
}