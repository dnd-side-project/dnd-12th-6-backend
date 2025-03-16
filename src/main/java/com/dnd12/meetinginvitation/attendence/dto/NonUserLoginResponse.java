package com.dnd12.meetinginvitation.attendence.dto;

import com.dnd12.meetinginvitation.invitation.enums.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class NonUserLoginResponse {
    private Long invitationId;
    private AttendanceStatus state;
    private String name;
    private String message;
    private String token;
}
