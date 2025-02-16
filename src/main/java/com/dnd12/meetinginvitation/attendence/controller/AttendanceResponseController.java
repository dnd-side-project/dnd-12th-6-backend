package com.dnd12.meetinginvitation.attendence.controller;

import com.dnd12.meetinginvitation.attendence.dto.AttendanceRequest;
import com.dnd12.meetinginvitation.attendence.dto.UserAttendanceRequest;
import com.dnd12.meetinginvitation.attendence.entity.Attendance;
import com.dnd12.meetinginvitation.attendence.service.AttendanceService;
import com.dnd12.meetinginvitation.common.ApiResponse;
import com.dnd12.meetinginvitation.user.dto.LoginResponse;
import com.dnd12.meetinginvitation.user.service.KakaoLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.batch.BatchTaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
@Slf4j
public class AttendanceResponseController {

    private final AttendanceService attendanceService;
    private final KakaoLoginService kakaoLoginService;

    //비회원 로그인 후 응답
    @PostMapping("/nonUser/response")
    public ResponseEntity<ApiResponse<Void>> AttendanceResponse(@RequestBody AttendanceRequest request) {
        attendanceService.saveAttendance(request);
        return ResponseEntity.ok(ApiResponse.success("비회원 참석자 응답 성공"));
    }

    @PostMapping("/user/response")
    public ResponseEntity<ApiResponse<Void>> UserAttendanceResponse(@RequestBody UserAttendanceRequest userAttendanceRequest) {
        attendanceService.saveUserAttendance(userAttendanceRequest);
        return ResponseEntity.ok(ApiResponse.success("회원 참석자 응답 성공"));
    }

    //회원 로그인 후 응답
    @GetMapping("/login")
    public void attendanceLogin(@RequestParam("code") String code, HttpServletResponse response) {
        try {
            LoginResponse loginResponse = kakaoLoginService.handleKakaoLogin(code);
            String encodedName = URLEncoder.encode(loginResponse.getName(), "UTF-8");

            //쿠키 생성
            Cookie accessTokenCookie = new Cookie("AccessToken", loginResponse.getAccessToken());
            accessTokenCookie.setHttpOnly(true); //JavaScript에서 접근 불가능하게 설정
            accessTokenCookie.setSecure(false); //HTTPS에서만 전송
            accessTokenCookie.setPath("/"); //모든 경로에서 접근 가능
            accessTokenCookie.setMaxAge(3600); // 쿠키 유효시간 설정(1시간)

            //응답에 쿠키 추가
            response.addCookie(accessTokenCookie);

            String redirectUrl = String.format(
                    "http://localhost:3000/invitation/answer?userId=%s&name=%s&profileImageUrl=%s&email=%s",
                    loginResponse.getUserId(),
                    encodedName,
                    loginResponse.getProfileImageUrl(),
                    loginResponse.getEmail()
            );

            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("Redirect failed: ", e);
        }
    }
}
