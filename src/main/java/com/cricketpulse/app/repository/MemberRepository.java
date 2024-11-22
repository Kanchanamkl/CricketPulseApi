package com.cricketpulse.app.repository;

import com.cricketpulse.app.entity.Member;
import com.cricketpulse.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Kanchana Kalansooriya
 * Since: 11/10/2024
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser(User user);

}