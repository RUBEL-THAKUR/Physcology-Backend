package com.healthcare.bean.controller;

import com.healthcare.bean.dto.*;
import com.healthcare.bean.model.TherapistStatus;
import com.healthcare.bean.service.TherapistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/therapist")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TherapistController {

    private final TherapistService therapistService;

    // SIGNUP
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

            TherapistResponseDTO response = therapistService.signupTherapist(request, cvFile);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Therapist registered successfully. Your application is pending approval.");
            result.put("data", response);

            return ResponseEntity.ok(result);

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

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> loginTherapist(@RequestBody TherapistLoginRequest request) {
        try {
            TherapistResponseDTO response = therapistService.loginTherapist(request);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Login successful");
            result.put("data", response);

            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TherapistResponseDTO> getTherapistById(@PathVariable UUID id) {
        TherapistResponseDTO response = therapistService.getTherapistById(id);
        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TherapistResponseDTO>> getAllTherapists() {
        List<TherapistResponseDTO> therapists = therapistService.getAllTherapists();
        return ResponseEntity.ok(therapists);
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TherapistResponseDTO>> getTherapistsByStatus(@PathVariable TherapistStatus status) {
        List<TherapistResponseDTO> therapists = therapistService.getTherapistsByStatus(status);
        return ResponseEntity.ok(therapists);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TherapistResponseDTO> updateTherapist(
            @PathVariable UUID id,
            @RequestBody TherapistUpdateRequest request
    ) {
        TherapistResponseDTO response = therapistService.updateTherapist(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTherapist(@PathVariable UUID id) {
        therapistService.deleteTherapist(id);
        return ResponseEntity.ok("Therapist deleted successfully");
    }

    // APPROVE THERAPIST
    @PutMapping("/{id}/approve")
    public ResponseEntity<TherapistResponseDTO> approveTherapist(
            @PathVariable UUID id,
            @RequestParam String approvedBy
    ) {
        TherapistResponseDTO response = therapistService.updateStatus(id, TherapistStatus.APPROVED, null, approvedBy);
        return ResponseEntity.ok(response);
    }

    // REJECT THERAPIST
    @PutMapping("/{id}/reject")
    public ResponseEntity<TherapistResponseDTO> rejectTherapist(
            @PathVariable UUID id,
            @RequestParam String reason
    ) {
        TherapistResponseDTO response = therapistService.updateStatus(id, TherapistStatus.REJECTED, reason, null);
        return ResponseEntity.ok(response);
    }
}