package com.cricketpulse.app.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/15/2024
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COURT")
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courtId;
    private String courtName;
    private String courtImg;
    private String courtType; //indoor or  outdoor
}