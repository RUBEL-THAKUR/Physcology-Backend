// Location: src/main/java/com/healthcare/bean/controller/TherapistController.java

package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistSignupRequest;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistStatus;
import com.healthcare.bean.service.TherapistService;
import com.healthcare.bean.repository.TherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/therapist")
@CrossOrigin(origins = "*")
public class TherapistController {

    @Autowired
    private TherapistService therapistService;

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signupTherapist(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("emailId") String emailId,
            @RequestParam("password") String password,
            @RequestParam("mobileNumber") String mobileNumber,
            @RequestParam("isAbove18") boolean isAbove18,
            @RequestParam("acceptedTerms") boolean acceptedTerms,
            @RequestParam("cv") MultipartFile cvFile
    ) {
        try {
            TherapistSignupRequest request = new TherapistSignupRequest();
            request.setFirstName(firstName);
            request.setLastName(lastName);
            request.setEmailId(emailId);
            request.setPassword(password);
            request.setMobileNumber(mobileNumber);
            request.setAbove18(isAbove18);
            request.setAcceptedTerms(acceptedTerms);

            Therapist therapist = therapistService.signupTherapist(request, cvFile);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Therapist registered successfully. Your application is pending approval.");
            response.put("therapistId", therapist.getId());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    // JSON API for testing (without file upload)
    @PostMapping("/signup-json")
    public ResponseEntity<?> signupTherapistJSON(@RequestBody TherapistSignupRequest request) {
        try {
            if (therapistRepository.existsByEmailId(request.getEmailId())) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email already registered"));
            }

            if (therapistRepository.existsByMobileNumber(request.getMobileNumber())) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Mobile number already registered"));
            }

            if (!request.isAbove18()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "You must be above 18 years"));
            }

            if (!request.isAcceptedTerms()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "You must accept terms and conditions"));
            }

            Therapist therapist = new Therapist();
            therapist.setFirstName(request.getFirstName());
            therapist.setLastName(request.getLastName());
            therapist.setEmailId(request.getEmailId());
            therapist.setPassword(passwordEncoder.encode(request.getPassword())); // ENCRYPTED
            therapist.setMobileNumber(request.getMobileNumber());
            therapist.setCvFileName("pending-upload.pdf");
            therapist.setAbove18(request.isAbove18());
            therapist.setAcceptedTerms(request.isAcceptedTerms());
            therapist.setStatus(TherapistStatus.PENDING);

            Therapist saved = therapistRepository.save(therapist);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Therapist registered successfully (CV pending)",
                    "therapistId", saved.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Error: " + e.getMessage()));
        }
    }
}