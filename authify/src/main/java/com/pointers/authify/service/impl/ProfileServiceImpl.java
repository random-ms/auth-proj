package com.pointers.authify.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pointers.authify.entity.UserEntity;
import com.pointers.authify.io.ProfileRequest;
import com.pointers.authify.io.ProfileResponse;
import com.pointers.authify.mapper.ProfileMapper;
import com.pointers.authify.repository.UserRepository;
import com.pointers.authify.service.EmailService;
import com.pointers.authify.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    final long MINUTES_15 = 9_000_000;
    final long HOURS_24 = 84_400_0000;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        UserEntity newProfile = ProfileMapper.convertToUserEntity(request);
        newProfile.setPassword(passwordEncoder.encode(request.getPassword()));
        if(!userRepository.existsByEmail(request.getEmail())){
            newProfile = userRepository.save(newProfile);
            return ProfileMapper.convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }
    
    @Override
    public ProfileResponse getProfile(String email) {
        return ProfileMapper.convertToProfileResponse(getUserFromDb(email));
    }

    private UserEntity getUserFromDb(String email){
            return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found."));
        }

    private String generateOtp(){
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    private long expiryTime(long time){
        return System.currentTimeMillis() + (time);
    }
    @Override
    public void sendResetOtp(String email) {
        UserEntity entity = getUserFromDb(email);

        //Generate 6 digit Otp
        String otp = generateOtp();

        // expiry time 15minutes
        long expiryTime = expiryTime(MINUTES_15);

        // update entity
        entity.setResetOtp(otp);
        entity.setResetOtpExpireAt(expiryTime);

        userRepository.save(entity);

        try {
           emailService.sendResetOtpEmail(entity.getEmail(), otp); 
        } catch (Exception e) {
            throw new RuntimeException("Unable to send email.");
        }
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity existingUser = getUserFromDb(email);

        if(existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)){
            throw new RuntimeException("Invalid Otp.");
        }

        if(existingUser.getResetOtpExpireAt() < System.currentTimeMillis()){
          throw new RuntimeException("Otp expired.");  
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpireAt(0L);

        userRepository.save(existingUser);
    }

    @Override
    public void sendAccountVerificationOtp(String email) {
        UserEntity user = getUserFromDb(email);

        if(user.getIsAccountVerified() != null && user.getIsAccountVerified()) return;

        String otp = generateOtp();

        // expiry time 24hours
        long expiryTime = expiryTime(HOURS_24);

        // Update userEntity
        user.setVerifyOtp(otp);
        user.setVerifyOtpExpireAt(expiryTime);

        // save to database
        userRepository.save(user);

        try {
            emailService.sendAccountVerificationOtpEmail(user.getEmail(), otp);
        } catch (Exception e) {
            throw new RuntimeException("Unable to send email.");
        }
    }

    @Override
    public void verifyOtp(String email, String otp) {
        UserEntity user = getUserFromDb(email);

        if(user.getVerifyOtp() == null || !user.getVerifyOtp().equals(otp)){
            throw new RuntimeException("Invalid Otp.");
        }

        if(user.getVerifyOtpExpireAt() < System.currentTimeMillis()){
            throw new RuntimeException("Otp expired.");
        }

        user.setIsAccountVerified(true);
        user.setVerifyOtp(null);
        user.setVerifyOtpExpireAt(0L);

        userRepository.save(user);
    }    
}