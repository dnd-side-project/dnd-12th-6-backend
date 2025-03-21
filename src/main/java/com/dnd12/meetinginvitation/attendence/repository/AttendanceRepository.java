package com.dnd12.meetinginvitation.attendence.repository;

import com.dnd12.meetinginvitation.attendence.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByInvitationId(Long invitationId);
    boolean existsByName(String name);
    boolean existsByUser_Id(Long userId);
    Attendance findByInvitationIdAndName(Long invitationId, String name);
    Optional<Attendance> findByInvitationIdAndUserId(Long invitationId, Long userId);
}
