package com.cricketpulse.app.service;

import com.cricketpulse.app.dto.CoachBookingDTO;
import com.cricketpulse.app.dto.TimeSlotDTO;
import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.CoachBooking;
import com.cricketpulse.app.entity.Member;
import com.cricketpulse.app.entity.TimeSlot;
import com.cricketpulse.app.repository.CoachBookingRepository;
import com.cricketpulse.app.exception.CoachBookingNotFoundException;
import com.cricketpulse.app.repository.CoachRepository;
import com.cricketpulse.app.repository.MemberRepository;
import com.cricketpulse.app.repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachBookingService {

    private final CoachBookingRepository coachBookingRepository;
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;
    private final TimeSlotRepository timeSlotRepository;


    @Transactional
    public CoachBooking createCoachBooking(CoachBookingDTO coachBookingDTO) {
        Coach coach = coachRepository.findById(coachBookingDTO.getCoachId())
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach not found with id: " + coachBookingDTO.getCoachId()));

        Member member = memberRepository.findById(coachBookingDTO.getMemberId())
                .orElseThrow(() -> new CoachBookingNotFoundException("Member not found with id: " + coachBookingDTO.getMemberId()));

        CoachBooking coachBooking = CoachBooking.builder()
                .coach(coach)
                .member(member)
                .date(coachBookingDTO.getDate())
                .slotCount(coachBookingDTO.getSlotCount())
                .build();

        // Save the CoachBooking entity first
        coachBooking = coachBookingRepository.save(coachBooking);

        // Save the associated TimeSlot entities
        for (TimeSlot ts : coachBookingDTO.getTimeSlots()) {
            TimeSlot timeSlot = TimeSlot.builder()
                    .coachBooking(coachBooking)
                    .startTime(ts.getStartTime())
                    .endTime(ts.getEndTime())
                    .description(ts.getDescription())
                    .build();
            timeSlotRepository.save(timeSlot);
        }

        return coachBooking;
    }

    public CoachBooking getCoachBookingById(Long id) {
        return coachBookingRepository.findById(id)
                .orElseThrow(() -> new CoachBookingNotFoundException("Coach booking not found with id: " + id));
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
        existingCoachBooking.setSlotCount(coachBookingDTO.getSlotCount());
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


    public List<TimeSlot> getBookedTimeSlotsByCoachAndDate(Long coachId, LocalDate date) {
        return timeSlotRepository.findByCoachBooking_Coach_IdAndCoachBooking_Date(coachId, date);
    }

}