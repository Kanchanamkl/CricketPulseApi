package com.cricketpulse.app.service;

import com.cricketpulse.app.dto.CoachBookingDTO;
import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.Member;
import com.cricketpulse.app.repository.CoachBookingRepository;
import com.cricketpulse.app.exception.CoachBookingNotFoundException;
import com.cricketpulse.app.repository.CoachRepository;
import com.cricketpulse.app.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachBookingService {

    private final CoachBookingRepository coachBookingRepository;
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;
    private final UserService userService;


    @Transactional
    public CoachBooking createCoachBooking(CoachBookingDTO coachBookingDTO) {
        Coach coach = coachRepository.findById(coachBookingDTO.getCoachId())
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach not found with id: " + coachBookingDTO.getCoachId()));

        Member member = memberRepository.findByUser(userService.getUserById(coachBookingDTO.getMemberId()))
                .orElseThrow(() -> new CoachBookingNotFoundException("Member not found with id: " + coachBookingDTO.getMemberId()));



        CoachBooking coachBooking = CoachBooking.builder()
                .coach(coach)
                .member(member)
                .date(coachBookingDTO.getDate())
                .startTime(coachBookingDTO.getStartTime())
                .endTime(coachBookingDTO.getEndTime())
                .description(coachBookingDTO.getDescription())
                .build();

        coachBooking = coachBookingRepository.save(coachBooking);
        return coachBooking;
    }

    public CoachBooking getCoachBookingById(Long id) {
        return coachBookingRepository.findById(id)
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach booking not found with id: " + id));
    }

    public List<CoachBooking> getCoachBookingsByMemberId(Long memberId) {
        return coachBookingRepository.findAllByMemberId(memberId);
    }

    public List<CoachBooking> getCoachBookingsByCoachId(Long coachId) {
        return coachBookingRepository.findAllByCoachId(coachId);
    }

    public List<String> getCoachBookingSlotsByDateAndCoachId(LocalDate date , Long CoachId) {
        Coach coach = coachRepository.findById(CoachId)
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach not found with id: " +CoachId));
        List<CoachBooking> coachBookings = coachBookingRepository.findCoachBookingsByDateAndCoach(date, coach);
        List<String> slots = new ArrayList<>();
        for (CoachBooking booking : coachBookings) {
            String slot = booking.getStartTime().toString() + " - " + booking.getEndTime().toString();
            slots.add(slot);
        }
        return slots;
    }


    public List<CoachBooking> getAllCoachBookings() {
        return coachBookingRepository.findAll();
    }

    @Transactional
    public CoachBooking updateCoachBooking(Long id, CoachBookingDTO coachBookingDTO) {
        CoachBooking existingCoachBooking = coachBookingRepository.findById(id)
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach booking not found with id: " + id));

        Coach coach = coachRepository.findById(coachBookingDTO.getCoachId())
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach not found with id: " + coachBookingDTO.getCoachId()));

        Member member = memberRepository.findById(coachBookingDTO.getMemberId())
                .orElseThrow(() -> new CoachBookingNotFoundException("Member not found with id: " + coachBookingDTO.getMemberId()));

        existingCoachBooking.setCoach(coach);
        existingCoachBooking.setMember(member);
        existingCoachBooking.setDate(coachBookingDTO.getDate());
        existingCoachBooking.setStartTime(coachBookingDTO.getStartTime());
        existingCoachBooking.setEndTime(coachBookingDTO.getEndTime());
        existingCoachBooking.setDescription(coachBookingDTO.getDescription());
//        existingCoachBooking.setTimeSlots(coachBookingDTO.getTimeSlots());

        return coachBookingRepository.save(existingCoachBooking);
    }

    @Transactional
    public void deleteCoachBooking(Long id) {
        if (!coachBookingRepository.existsById(id)) {
            throw new CoachBookingNotFoundException("Coach booking not found with id: " + id);
        }
        coachBookingRepository.deleteById(id);
    }


}