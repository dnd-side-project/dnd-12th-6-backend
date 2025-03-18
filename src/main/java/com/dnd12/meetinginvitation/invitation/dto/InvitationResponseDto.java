package com.dnd12.meetinginvitation.invitation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponseDto {
    private Long invitationId;
    private String inviteKey;
}
