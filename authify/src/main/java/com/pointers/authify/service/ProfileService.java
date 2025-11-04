package com.pointers.authify.service;

import com.pointers.authify.io.ProfileRequest;
import com.pointers.authify.io.ProfileResponse;

public interface ProfileService {

   ProfileResponse createProfile(ProfileRequest profileRequest);
   ProfileResponse getProfile(String email);
   void sendResetOtp(String email);
   void resetPassword(String email, String otp, String newPassword);
   void sendAccountVerificationOtp(String email);
   void verifyOtp(String email, String otp);
}