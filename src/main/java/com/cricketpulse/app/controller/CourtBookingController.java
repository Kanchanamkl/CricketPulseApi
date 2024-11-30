package com.cricketpulse.app.controller;
import com.cricketpulse.app.dto.CourtBookingDTO;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.Court;
import com.cricketpulse.app.entity.CourtBooking;
import com.cricketpulse.app.entity.Member;
import com.cricketpulse.app.repository.MemberRepository;
import com.cricketpulse.app.service.CourtBookingService;
import com.cricketpulse.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/court-bookings")
@RequiredArgsConstructor
public class CourtBookingController {

    private final CourtBookingService courtBookingService;
    private final MemberRepository memberRepository;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CourtBooking> createCourtBooking(@RequestBody CourtBookingDTO courtBookingDTO) {
        CourtBooking createdCourtBooking = courtBookingService.createCourtBooking(courtBookingDTO);
        return ResponseEntity.ok(createdCourtBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtBooking> getCourtBookingById(@PathVariable Long id) {
        CourtBooking courtBooking = courtBookingService.getCourtBookingById(id);
        return ResponseEntity.ok(courtBooking);
    }

    @GetMapping("/get_all_court_bookings")
    public ResponseEntity<List<CourtBooking>> getAllCourtBookings() {
        List<CourtBooking> courtBookings = courtBookingService.getAllCourtBookings();
        return ResponseEntity.ok(courtBookings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtBooking> updateCourtBooking(@PathVariable Long id, @RequestBody CourtBookingDTO courtBookingDTO) {
        CourtBooking updatedCourtBooking = courtBookingService.updateCourtBooking(id, courtBookingDTO);
        return ResponseEntity.ok(updatedCourtBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourtBooking(@PathVariable Long id) {
        courtBookingService.deleteCourtBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courts")
    public ResponseEntity<List<Court>> getAllCourts() {
        List<Court> courts = courtBookingService.getAllCourts();
        return ResponseEntity.ok(courts);
    }

    @GetMapping("/get_court_bookings_by_member/{userId}")
    public List<CourtBooking> getCourtBookingsByMemberId(@PathVariable Long userId) {
//        Optional<Member> member = memberRepository.findByUser(userService.getUserById(userId));
//        System.out.println("Member: " + member.get().getId());
        List<CourtBooking> courtBookings = courtBookingService.getCourtBookingsByMemberId(userId);
        return courtBookings;

    }

}