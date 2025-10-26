package com.healthcare.bean.service;

import com.healthcare.bean.model.OtpEntity;
import com.healthcare.bean.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {



    // ----------------   This is Store Otp in database

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(String identifier) {

        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        OtpEntity entity = new OtpEntity();
        entity.setIdentifier(identifier);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(entity);


        // MOCK sending OTP & print ON Console
        System.out.println("OTP sent to " + identifier + ": " + otp);
        return otp;
    }


    public boolean verifyOtp(String identifier, String otp) {
        return otpRepository.findTopByIdentifierAndIsUsedFalseOrderByExpiryTimeDesc(identifier)
                .filter(entry -> entry.getOtp().equals(otp) && entry.getExpiryTime().isAfter(LocalDateTime.now()))
                .map(entry -> {
                    entry.setIsUsed(true);
                    otpRepository.save(entry);
                    return true;
                }).orElse(false);
    }



    @Autowired
    private EmailService emailService;

    private final Map<String, String> otpStorage = new HashMap<>();


    @Autowired
    private SmsService smsService;


    // its used for both  [ sms  and email ]
    public void generateAndSendOtp(String identifier) {
        String otp = generateOtp(identifier);

        if (identifier.contains("@")) {
            emailService.sendOtpEmail(identifier, otp);
        } else {
            smsService.sendOtpSms(identifier, otp);  //  ye mobile ke liye hai
        }
    }

}

