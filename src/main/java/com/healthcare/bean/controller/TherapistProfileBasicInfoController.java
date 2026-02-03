//package com.healthcare.bean.controller;
//
//import com.healthcare.bean.dto.TherapistProfileBasicInfoRequest;
//import com.healthcare.bean.model.TherapistProfile;
//import com.healthcare.bean.service.TherapistProfileService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/profile")
//public class TherapistProfileBasicInfoController {
//
//    private final TherapistProfileService service;
//
//    public TherapistProfileBasicInfoController(TherapistProfileService service) {
//        this.service = service;
//    }
//
//    @PostMapping("/basic-info/{therapistId}")
//    public ResponseEntity<TherapistProfile> saveBasicInfo(
//            @PathVariable UUID therapistId,
//            @RequestBody TherapistProfileBasicInfoRequest request) {
//
//        TherapistProfile savedProfile = service.saveBasicInfo(therapistId, request);
//        return ResponseEntity.ok(savedProfile);
//    }
//
//    @GetMapping("/basic-info/{therapistId}")
//    public ResponseEntity<TherapistProfile> getBasicInfo(@PathVariable UUID therapistId) {
//        TherapistProfile profile = service.getBasicInfo(therapistId);
//        return ResponseEntity.ok(profile);
//    }
//
//    @DeleteMapping("/basic-info/{therapistId}")
//    public ResponseEntity<String> deleteBasicInfo(@PathVariable UUID therapistId) {
//        service.deleteBasicInfo(therapistId);
//        return ResponseEntity.ok("Profile deleted successfully for therapist ID: " + therapistId);
//    }
//}

package com.healthcare.bean.controller;

import com.healthcare.bean.dto.TherapistProfileBasicInfoRequest;
import com.healthcare.bean.model.TherapistProfile;
import com.healthcare.bean.service.TherapistProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class TherapistProfileBasicInfoController {

    private final TherapistProfileService service;

    public TherapistProfileBasicInfoController(TherapistProfileService service) {
        this.service = service;
    }

    // ✅ SAVE / UPDATE BASIC INFO
    @PostMapping("/basic-info")
    public ResponseEntity<TherapistProfile> saveBasicInfo(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody TherapistProfileBasicInfoRequest request
    ) {
        String token = authHeader.substring(7);
        TherapistProfile savedProfile = service.saveBasicInfo(token, request);
        return ResponseEntity.ok(savedProfile);
    }

    // ✅ GET BASIC INFO
    @GetMapping("/basic-info")
    public ResponseEntity<TherapistProfile> getBasicInfo(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        TherapistProfile profile = service.getBasicInfo(token);
        return ResponseEntity.ok(profile);
    }

    // ✅ DELETE BASIC INFO
    @DeleteMapping("/basic-info")
    public ResponseEntity<String> deleteBasicInfo(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        service.deleteBasicInfo(token);
        return ResponseEntity.ok("Profile deleted successfully");
    }
}
