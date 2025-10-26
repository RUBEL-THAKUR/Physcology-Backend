package com.healthcare.bean.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

    private static final String API_KEY = "kSTW8B6uPAMQECDJpaRrjshL2Nq1UHxy7XI95Zf4O0KGzFiYmVD4hRHnvbtlx5I1STsYZQ2e7OmuGiyg"; // Apna actual key yahan daalein

    public void sendOtpSms(String mobileNumber, String otp) {
        try {
            String url = "https://www.fast2sms.com/dev/bulkV2?"
                    + "authorization=" + API_KEY
                    + "&route=otp"
                    + "&variables_values=" + otp
                    + "&flash=0"
                    + "&numbers=" + mobileNumber;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            System.out.println("SMS sent response: " + response);
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }
}
