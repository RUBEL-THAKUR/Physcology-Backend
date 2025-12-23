package com.healthcare.bean.service;

import com.healthcare.bean.dto.TherapistProfileBasicInfoRequest;
import com.healthcare.bean.model.TherapistProfile;
import com.healthcare.bean.repository.TherapistProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class TherapistProfileService {

    private final TherapistProfileRepository repository;

    public TherapistProfileService(TherapistProfileRepository repository) {
        this.repository = repository;
    }

    public TherapistProfile saveBasicInfo(
            TherapistProfileBasicInfoRequest req) {

        TherapistProfile profile = new TherapistProfile();

        profile.setPrefix(req.getPrefix());
        profile.setFirstName(req.getFirstName());
        profile.setMiddleName(req.getMiddleName());
        profile.setLastName(req.getLastName());
        profile.setCategory(req.getCategory());
        profile.setEmailId(req.getEmailId());
        profile.setUserName(req.getUserName());
        profile.setMobile(req.getMobile());
        profile.setDateOfBirth(req.getDateOfBirth());
        profile.setGender(req.getGender());
        profile.setLanguage(req.getLanguage());
        profile.setBriefDescription(req.getBriefDescription());

        profile.setPresentAddress(req.getPresentAddress());
        profile.setPresentCountry(req.getPresentCountry());
        profile.setPresentState(req.getPresentState());
        profile.setPresentCity(req.getPresentCity());
        profile.setPresentDistrict(req.getPresentDistrict());
        profile.setPresentPinCode(req.getPresentPinCode());

        if (req.isSameAsPresentAddress()) {
            profile.setClinicAddress(req.getPresentAddress());
            profile.setClinicCountry(req.getPresentCountry());
            profile.setClinicState(req.getPresentState());
            profile.setClinicCity(req.getPresentCity());
            profile.setClinicDistrict(req.getPresentDistrict());
            profile.setClinicPinCode(req.getPresentPinCode());
        } else {
            profile.setClinicAddress(req.getClinicAddress());
            profile.setClinicCountry(req.getClinicCountry());
            profile.setClinicState(req.getClinicState());
            profile.setClinicCity(req.getClinicCity());
            profile.setClinicDistrict(req.getClinicDistrict());
            profile.setClinicPinCode(req.getClinicPinCode());
        }

        profile.setTimeZone(req.getTimeZone());
        profile.setExperience(req.getExperience());

        return repository.save(profile);
    }
}
