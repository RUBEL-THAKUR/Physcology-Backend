package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistProfileBasicInfoRequest;
import com.healthcare.bean.service.TherapistProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class TherapistProfileBasicInfoController {

    private final TherapistProfileService service;

    public TherapistProfileBasicInfoController(
            TherapistProfileService service) {
        this.service = service;
    }

    @PostMapping("/basic-info")
    public ResponseEntity<?> saveBasicInfo(
            @RequestBody TherapistProfileBasicInfoRequest request) {

        return ResponseEntity.ok(
                service.saveBasicInfo(request)
        );
    }
}
