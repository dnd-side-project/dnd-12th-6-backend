package com.dnd12.meetinginvitation.attendence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NonUserLoginRequest {
    private Long invitationId;
    private String name;
    private String password;
}
