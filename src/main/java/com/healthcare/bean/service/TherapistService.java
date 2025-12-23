// Location: src/main/java/com/healthcare/bean/service/TherapistService.java

package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistSignupRequest;
import com.healthcare.bean.model.Therapist;
import com.healthcare.bean.model.TherapistStatus;
import com.healthcare.bean.repository.TherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class TherapistService {

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.upload.cv.dir:uploads/cv/}")
    private String uploadDir;

    public Therapist signupTherapist(TherapistSignupRequest request, MultipartFile cvFile) throws IOException {
        // Validation
        if (therapistRepository.existsByEmailId(request.getEmailId())) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (therapistRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        if (!request.isAbove18()) {
            throw new IllegalArgumentException("You must be above 18 years");
        }

        if (!request.isAcceptedTerms()) {
            throw new IllegalArgumentException("You must accept terms and conditions");
        }

        if (cvFile == null || cvFile.isEmpty()) {
            throw new IllegalArgumentException("CV is required");
        }

        // Save CV file
        String cvFileName = saveCV(cvFile);

        // Create therapist with encrypted password
        Therapist therapist = new Therapist();
        therapist.setFirstName(request.getFirstName());
        therapist.setLastName(request.getLastName());
        therapist.setEmailId(request.getEmailId());
        therapist.setPassword(passwordEncoder.encode(request.getPassword())); // ENCRYPTED
        therapist.setMobileNumber(request.getMobileNumber());
        therapist.setCvFileName(cvFileName);
        therapist.setAbove18(request.isAbove18());
        therapist.setAcceptedTerms(request.isAcceptedTerms());
        therapist.setStatus(TherapistStatus.PENDING);

        return therapistRepository.save(therapist);
    }

    private String saveCV(MultipartFile file) throws IOException {
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path filePath = Paths.get(uploadDir + uniqueFilename);
        Files.write(filePath, file.getBytes());

        return uniqueFilename;
    }

    public String getCVFullPath(String fileName) {
        return uploadDir + fileName;
    }
}