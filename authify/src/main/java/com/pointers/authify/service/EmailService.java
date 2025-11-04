package com.pointers.authify.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    private final String welcomeSubject = "Welcome to our platform";
    private final String body = "Thanks for registering with us!";
    private final String footer = "Regards,\nAuthify Team";

    public void sendWelcomeEmail(String toAddress, String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toAddress);
        message.setSubject(welcomeSubject);
        message.setText("Hello " + name + ",\n\n" + body +"\n\n" + footer);

        javaMailSender.send(message);
    }

    // private final String resetSubject = "Password Reset OTP";
    // private final String resetBody = "Your OTP for resetting password is ";

    // public void sendResetOtpEmail(String toAddress, String otp){
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setFrom(fromEmail);
    //     message.setTo(toAddress);
    //     message.setSubject(resetSubject);
    //     message.setText(resetBody + otp + ". \nValid for 15 minutes.\n\n" + footer);

    //     javaMailSender.send(message);
    // }

    // private final String verificationSubject = "Account Verification OTP";
    // private final String verificationBody = "Verify your account using the following OTP, ";

    // public void sendAccountVerificationOtpEmail(String toAddress, String otp){
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setFrom(fromEmail);
    //     message.setTo(toAddress);
    //     message.setSubject(verificationSubject);
    //     message.setText(verificationBody + otp + ". \nValid for 24 hours.\n\n" + footer);

    //     javaMailSender.send(message);
    // }

    public void sendAccountVerificationOtpEmail(String toEmail, String otp) throws MessagingException {
        Context context = new Context();
        context.setVariable("email", toEmail);
        context.setVariable("otp", otp);

        String process = templateEngine.process("verify-email", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Account Verification OTP");
        helper.setText(process, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendResetOtpEmail(String toEmail, String otp) throws MessagingException {
        Context context = new Context();
        context.setVariable("email", toEmail);
        context.setVariable("otp", otp);

        String process = templateEngine.process("password-reset-email", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Forgot your password?");
        helper.setText(process, true);

        javaMailSender.send(mimeMessage);
    }
}