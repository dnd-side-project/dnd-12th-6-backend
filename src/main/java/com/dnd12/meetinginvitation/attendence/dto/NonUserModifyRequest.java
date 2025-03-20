package com.dnd12.meetinginvitation.attendence.dto;

import com.dnd12.meetinginvitation.invitation.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NonUserModifyRequest {
    private AttendanceStatus state;
    private String newMessage;
}
