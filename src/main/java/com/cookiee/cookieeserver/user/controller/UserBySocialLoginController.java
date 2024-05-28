package com.cookiee.cookieeserver.user.controller;

import com.cookiee.cookieeserver.global.SuccessCode;
import com.cookiee.cookieeserver.login.jwt.JwtService;
import com.cookiee.cookieeserver.user.domain.User;
import com.cookiee.cookieeserver.global.dto.BaseResponseDto;
import com.cookiee.cookieeserver.global.dto.DataResponseDto;
import com.cookiee.cookieeserver.user.dto.request.UpdateUserRequestDto;
import com.cookiee.cookieeserver.user.dto.response.UserBySocialLoginResponseDto;
import com.cookiee.cookieeserver.user.repository.UserRepository;
import com.cookiee.cookieeserver.user.service.UserBySocialLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.cookiee.cookieeserver.global.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@Tag(name="소셜로그인 유저 프로필 RU", description="소셜로그인 유저의 프로필을 조회/수정할 수 있습니다.")
public class UserBySocialLoginController {
    private final UserBySocialLoginService userBySocialLoginService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // health check에 대한 상태 반환 위해서
    @GetMapping("/healthcheck")
    @Operation(summary = "헬스체크")
    public BaseResponseDto healthcheck() {
        return BaseResponseDto.ofSuccess(SuccessCode.OK);
    }

    // 유저 프로필 조회
    @GetMapping("/users/{userId}")
    @Operation(summary = "유저 프로필 조회")
    public BaseResponseDto<UserBySocialLoginResponseDto> getUser(@PathVariable Long userId){
        final User currentUserV2 = jwtService.getAndValidateCurrentUser(userId);

        UserBySocialLoginResponseDto userBySocialLoginResponseDto = UserBySocialLoginResponseDto.builder()
                .userId(currentUserV2.getUserId())
                .email(currentUserV2.getEmail())
                .nickname(currentUserV2.getNickname())
                .profileImage(currentUserV2.getProfileImage())
                .selfDescription(currentUserV2.getSelfDescription())
                .categories(currentUserV2.getCategories().stream()
                        .map(category -> category.toDto(category))
                        .collect(Collectors.toList()))
                .build();

        return BaseResponseDto.ofSuccess(GET_USER_SUCCESS, userBySocialLoginResponseDto);
    }

    // 유저 프로필 수정 (닉네임, 한줄 소개, 프로필사진)
    @PutMapping(value = "/users/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "유저 프로필 수정")
    public BaseResponseDto<UserBySocialLoginResponseDto> updateUser(@PathVariable Long userId,
                                                                    UpdateUserRequestDto requestUser){
        final User currentUser = jwtService.getAndValidateCurrentUser(userId);

        User newUser = userBySocialLoginService.updateUser(currentUser, requestUser);
        userRepository.save(newUser);

        UserBySocialLoginResponseDto userBySocialLoginResponseDto = UserBySocialLoginResponseDto.builder()
                .userId(currentUser.getUserId())
                .email(currentUser.getEmail())
                .nickname(currentUser.getNickname())
                .profileImage(currentUser.getProfileImage())
                .selfDescription(currentUser.getSelfDescription())
                .categories(currentUser.getCategories().stream()
                        .map(category -> category.toDto(category))
                        .collect(Collectors.toList()))
                .build();

        return BaseResponseDto.ofSuccess(MODIFY_USER_SUCCESS, userBySocialLoginResponseDto);
    }

    // 임시로 User 추가
    // TODO: 삭제하기
    @PostMapping("/users/join")
    @Operation(summary = "임시 유저 추가")
    public DataResponseDto<Object> postTestUser(User user) {
        System.out.println("id: " + user.getUserId());
        System.out.println("nickname: " + user.getNickname());
        System.out.println("email: " + user.getEmail());
        System.out.println("profileImage: " + user.getProfileImage());
        System.out.println("describe: " + user.getSelfDescription());
        System.out.println("created date: " + user.getCreatedDate());

        userRepository.save(user);

        //return DataResponseDto.empty();
        return DataResponseDto.of("회원가입에 성공하였습니다.", null);
    }
}