package com.cricketpulse.app.service;
import com.cricketpulse.app.dto.CourtBookingDTO;
import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.Court;
import com.cricketpulse.app.entity.CourtBooking;
import com.cricketpulse.app.exception.CoachBookingNotFoundException;
import com.cricketpulse.app.repository.CourtBookingRepository;
import com.cricketpulse.app.repository.CourtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtBookingService {

    private final CourtBookingRepository courtBookingRepository;
    private final CourtRepository courtRepository;

    public CourtBooking createCourtBooking(CourtBookingDTO courtBookingDTO) {
        Court court = courtRepository.findById(courtBookingDTO.getCourtId())
                .orElseThrow(() -> new CoachBookingNotFoundException("Court not found with id: " + courtBookingDTO.getCourtId()));
        CourtBooking courtBooking = CourtBooking.builder()
                .court(court)
                .memberId(courtBookingDTO.getMemberId())
                .date(courtBookingDTO.getDate())
                .startTime(courtBookingDTO.getStartTime())
                .endTime(courtBookingDTO.getEndTime())
                .description(courtBookingDTO.getDescription())
                .build();
        return courtBookingRepository.save(courtBooking);
    }

    public CourtBooking getCourtBookingById(Long id) {
        return courtBookingRepository.findById(id).orElseThrow(() -> new RuntimeException("CourtBooking not found"));
    }

    public List<CourtBooking> getAllCourtBookings() {
        return courtBookingRepository.findAll();
    }

    public CourtBooking updateCourtBooking(Long id, CourtBookingDTO courtBookingDTO) {
        CourtBooking courtBooking = courtBookingRepository.findById(id).orElseThrow(() -> new RuntimeException("CourtBooking not found"));
        // Update properties from DTO to entity
        courtBooking.setId(courtBookingDTO.getCourtId());
        courtBooking.setMemberId(courtBookingDTO.getMemberId());
        courtBooking.setDate(courtBookingDTO.getDate());
        courtBooking.setStartTime(courtBookingDTO.getStartTime());
        courtBooking.setEndTime(courtBookingDTO.getEndTime());
        courtBooking.setDescription(courtBookingDTO.getDescription());
        return courtBookingRepository.save(courtBooking);
    }

    public void deleteCourtBooking(Long id) {
        courtBookingRepository.deleteById(id);
    }


    public List<Court> getAllCourts(){
       return courtRepository.findAll();
    }

    public List<CourtBooking> getCourtBookingsByMemberId(Long memberId) {
        System.out.println("Member: " + memberId);
        return courtBookingRepository.findAllByMemberId(memberId);
    }


    public List<String> getCourtBookingsByDateAndCourtId(LocalDate date, Long courtId) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new CoachBookingNotFoundException("court not found with id: " + courtId));
        List<CourtBooking> courtBookings = courtBookingRepository.findCourtBookingsByDateAndCourt(date,court);
        List<String> slots = new ArrayList<>();
        for (CourtBooking booking : courtBookings) {
            String slot = booking.getStartTime().toString() + " - " + booking.getEndTime().toString();
            slots.add(slot);
        }
        return slots;
    }

}