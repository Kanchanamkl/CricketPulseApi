package com.cricketpulse.app.controller;

import com.cricketpulse.app.dto.CoachBookingDTO;
import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.Member;
import com.cricketpulse.app.entity.User;
import com.cricketpulse.app.repository.CoachRepository;
import com.cricketpulse.app.repository.MemberRepository;
import com.cricketpulse.app.service.CoachBookingService;
import com.cricketpulse.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coach-bookings")
@RequiredArgsConstructor
public class CoachBookingController {

    private final CoachBookingService coachBookingService;
    private final UserService userService;
    private final MemberRepository memberRepository;
    private final CoachRepository coachRepository;

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

    @GetMapping("/get_all_coach_bookings")
    public List<CoachBooking> getAllCoachBookings() {
        return  coachBookingService.getAllCoachBookings();
    }

    @GetMapping("/get_coach_bookings_by_member/{userId}")
    public List<CoachBooking> getCoachBookingsByMemberId(@PathVariable Long userId) {
        Optional<Member> member = memberRepository.findByUser(userService.getUserById(userId));
        System.out.println("Member: " + member.get().getId());
        List<CoachBooking> coachBookings = coachBookingService.getCoachBookingsByMemberId(member.get().getId());
        return coachBookings;

    }

    @GetMapping("/get_coach_bookings_by_coach/{userId}")
    public List<CoachBooking> getCoachBookingsByCoachId(@PathVariable Long userId) {
        Optional<Coach> coach = coachRepository.findByUser(userService.getUserById(userId));
        System.out.println("Coach: " + coach.get().getId());
        List<CoachBooking> coachBookings = coachBookingService.getCoachBookingsByCoachId(coach.get().getId());
        return coachBookings;

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


}