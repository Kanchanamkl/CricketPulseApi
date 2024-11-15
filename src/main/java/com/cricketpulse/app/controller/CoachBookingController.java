package com.cricketpulse.app.controller;

import com.cricketpulse.app.dto.CoachBookingDTO;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.TimeSlot;
import com.cricketpulse.app.service.CoachBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/coach-bookings")
@RequiredArgsConstructor
public class CoachBookingController {

    private final CoachBookingService coachBookingService;

    @PostMapping("/create")
    public ResponseEntity<CoachBooking> createCoachBooking(@RequestBody CoachBookingDTO coachBookingDTO) {
        CoachBooking createdCoachBooking = coachBookingService.createCoachBooking(coachBookingDTO);
        return ResponseEntity.ok(createdCoachBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachBooking> getCoachBookingById(@PathVariable Long id) {
        CoachBooking coachBooking = coachBookingService.getCoachBookingById(id);
        return ResponseEntity.ok(coachBooking);
    }

    @GetMapping
    public ResponseEntity<List<CoachBooking>> getAllCoachBookings() {
        List<CoachBooking> coachBookings = coachBookingService.getAllCoachBookings();
        return ResponseEntity.ok(coachBookings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachBooking> updateCoachBooking(@PathVariable Long id, @RequestBody CoachBookingDTO coachBookingDTO) {
        CoachBooking updatedCoachBooking = coachBookingService.updateCoachBooking(id, coachBookingDTO);
        return ResponseEntity.ok(updatedCoachBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoachBooking(@PathVariable Long id) {
        coachBookingService.deleteCoachBooking(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/booked-time-slots")
    public List<TimeSlot> getBookedTimeSlotsByCoachAndDate(@RequestParam Long coachId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return coachBookingService.getBookedTimeSlotsByCoachAndDate(coachId, localDate);
    }
}