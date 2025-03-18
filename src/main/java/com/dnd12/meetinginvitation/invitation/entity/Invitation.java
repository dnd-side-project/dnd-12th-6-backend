package com.dnd12.meetinginvitation.invitation.entity;

import com.dnd12.meetinginvitation.attendence.entity.Attendance;
import com.dnd12.meetinginvitation.invitation.enums.InvitationState;
import com.dnd12.meetinginvitation.user.entity.User;
import com.dnd12.meetinginvitation.util.AESUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invitation")
@SequenceGenerator(
        name = "INVITATION_SEQ_GENERATOR",
        sequenceName = "INVITATION_SEQ", // 시퀸스 명
        initialValue = 1, // 초기 값
        allocationSize = 1 // 미리 할당 받을 시퀸스 수
)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long id;

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<InvitationParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.REMOVE)
    private List<Attendance> attendances = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
    private String organizerName;
    private String place;
    private String detailAddress;
    private LocalDateTime date;

    @Column(nullable = false)
    private int maxAttendences;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationState state; //초대 상태 (INPROGRESS, COMPLETED, CANCELED)
     
    private String link;

    private String title;

    // 초대장은 하나의 폰트를 사용
    @ManyToOne
    @JoinColumn(name = "font_id", nullable = true)
    private Font font;

    // 초대장은 하나의 스티커를 사용
    @ManyToOne
    @JoinColumn(name = "sticker_id", nullable = true)
    private Sticker sticker;

//    @OneToOne
//    @JoinColumn(name = "template_id", nullable = false)
//    private Background background;

    // 편지지 종류
    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = true)
    private Theme theme;

    private String backgroundUrl;

    private String basicBackgroundType;

    //외부 공개용 암호화된 서브키
    @Column(unique = true, nullable = false)
    private String inviteKey;

    @PrePersist
    public void generateInviteKey() {
        if (this.inviteKey == null) {
            this.inviteKey = AESUtil.encrypt(String.valueOf(this.id));  // PK 암호화 저장
        }
    }

    public String getDecryptedInviteKey() {
        return AESUtil.decrypt(this.inviteKey);
    }
}