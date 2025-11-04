package com.pointers.authify.io;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "Password is required")
    private String newPassword;

    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "Email is required")
    private String email;
}
