package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistResponseDTO;
import com.healthcare.bean.model.TherapistStatus;
import com.healthcare.bean.service.TherapistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/therapist")
@RequiredArgsConstructor
public class AdminTherapistController {

    private final TherapistService therapistService;

    @PutMapping("/{id}/approve")
    public ResponseEntity<TherapistResponseDTO> approve(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                therapistService.updateStatus(id, TherapistStatus.APPROVED, null, "ADMIN")
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<TherapistResponseDTO> reject(
            @PathVariable UUID id,
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(
                therapistService.updateStatus(id, TherapistStatus.REJECTED, reason, "ADMIN")
        );
    }
}
