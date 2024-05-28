package com.cookiee.cookieeserver.user.dto.response;

import com.cookiee.cookieeserver.category.dto.response.CategoryGetResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserBySocialLoginResponseDto {
    Long userId;
    String nickname;  // 사용자가 설정한 닉네임
    String email;  // 이메일
    String profileImage;  // 사용자 프로필 이미지 경로
    String selfDescription;  // 사용자가 설정한 한 줄 소개
    List<CategoryGetResponseDto> categories;

//    public UserResponseDto(){
//        this.userId = user.getUserId();
//        this.nickname = user.getNickname();
//        this.email = user.getEmail();
//        this.profileImage = user.getProfileImage();
//        this.selfDescription = user.getSelfDescription();
//        this.categories = user.getCategories();
//    }
}
