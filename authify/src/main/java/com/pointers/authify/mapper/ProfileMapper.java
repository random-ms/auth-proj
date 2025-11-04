package com.pointers.authify.mapper;

import java.util.UUID;

import com.pointers.authify.entity.UserEntity;
import com.pointers.authify.io.ProfileRequest;
import com.pointers.authify.io.ProfileResponse;

public class ProfileMapper {

    public static ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
            .name(newProfile.getName())
            .email(newProfile.getEmail())
            .userId(newProfile.getUserId())
            .isAccountVerified(newProfile.getIsAccountVerified())
            .build();
    }

    public static UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
            .email(request.getEmail())
            .userId(UUID.randomUUID().toString())
            .name(request.getName())
            .password(request.getPassword())
            .isAccountVerified(false)
            .resetOtpExpireAt(0L)
            .resetOtp(null)
            .verifyOtp(null)
            .build();
    }
}
