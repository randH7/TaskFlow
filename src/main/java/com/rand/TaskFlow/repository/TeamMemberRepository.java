package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {
    TeamMember findByUsername(String leaderUsername);
}
