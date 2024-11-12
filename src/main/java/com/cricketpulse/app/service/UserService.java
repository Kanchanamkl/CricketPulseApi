package com.cricketpulse.app.service;


import com.cricketpulse.app.dto.AuthenticationReqDTO;
import com.cricketpulse.app.dto.AuthenticationResDTO;
import com.cricketpulse.app.dto.UserDTO;
import com.cricketpulse.app.entity.Coach;
import com.cricketpulse.app.entity.Member;
import com.cricketpulse.app.enums.ROLE;
import com.cricketpulse.app.exception.UserAlreadyExistsException;
import com.cricketpulse.app.exception.UserNotFoundException;
import com.cricketpulse.app.repository.CoachRepository;
import com.cricketpulse.app.repository.MemberRepository;
import com.cricketpulse.app.repository.UserRepository;
import com.cricketpulse.app.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kanchana_m
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @Transactional
    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setProfilePic(userDTO.getProfilePic());
        User updatedUser = userRepository.save(user);

        if (user.getRole().equals(ROLE.COACH)) {
            Coach coach = coachRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Coach not found"));
            coach.setFirstName(userDTO.getFirstName());
            coach.setLastName(userDTO.getLastName());
            coach.setPhoneNumber(userDTO.getPhoneNumber());
            coach.setAddress(userDTO.getAddress());
            coach.setGender(userDTO.getGender());
            coach.setDob(userDTO.getDob());
            coach.setProfilePic(userDTO.getProfilePic());
            coachRepository.save(coach);
        } else if (user.getRole().equals(ROLE.MEMBER)) {
            Member member = memberRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Member not found"));
            member.setFirstName(userDTO.getFirstName());
            member.setLastName(userDTO.getLastName());
            member.setPhoneNumber(userDTO.getPhoneNumber());
            member.setAddress(userDTO.getAddress());
            member.setGender(userDTO.getGender());
            member.setDob(userDTO.getDob());
            member.setProfilePic(userDTO.getProfilePic());
            memberRepository.save(member);
        }

        return updatedUser;
    }

    @Transactional
    public AuthenticationResDTO createUser(UserDTO userDTO) {
        System.out.println("user-name :" + userDTO.getUsername());
        boolean isUserPresent = userRepository.findByUsername(userDTO.getUsername()).isPresent();

        if (isUserPresent) {
            throw new UserAlreadyExistsException("User Already Exists in the system");
        } else {
            User user = User.builder()
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .role(userDTO.getRole())
                    .profilePic(userDTO.getProfilePic())
                    .build();

            userRepository.save(user);

            if (userDTO.getRole().equals(ROLE.COACH)) {
                Coach coach = Coach.builder()
                        .user(user)
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .address(userDTO.getAddress())
                        .gender(userDTO.getGender())
                        .dob(userDTO.getDob())
                        .profilePic(userDTO.getProfilePic())
                        .build();
                coachRepository.save(coach);
            } else if (userDTO.getRole().equals(ROLE.MEMBER)) {
                Member member = Member.builder()
                        .user(user)
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .gender(userDTO.getGender())
                        .address(userDTO.getAddress())
                        .profilePic(userDTO.getProfilePic())
                        .dob(userDTO.getDob())
                        .build();
                memberRepository.save(member);
            }
        }
        return AuthenticationResDTO.builder()
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .role(userDTO.getRole().toString())
                .build();
    }


    @Transactional
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Sorry, user not found");
        }
        userRepository.deleteById(id);
        return true;
    }

    public AuthenticationResDTO authenticateUser(AuthenticationReqDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getEmail()).orElseThrow();
        return AuthenticationResDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().toString())
                .profilePic(user.getProfilePic())
                .message("")
                .responseCode(HttpStatus.OK)
                .build();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(ROLE role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(role))
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Sorry, no user found with the Id :" + id));
    }

    @Transactional
    public AuthenticationResDTO createAdmin(UserDTO userDTO) {
        boolean isUserPresent = userRepository.findByUsername(userDTO.getUsername()).isPresent();

        if (isUserPresent) {
            throw new UserAlreadyExistsException("User Already Exists in the system");
        } else {
            User user = User.builder()
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .role(ROLE.ADMIN) // Set the role to ADMIN
                    .profilePic(userDTO.getProfilePic())
                    .build();

            userRepository.save(user);
        }
        return AuthenticationResDTO.builder()
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .role(ROLE.ADMIN.toString())
                .build();
    }
}
