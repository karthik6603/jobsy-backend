package com.jobportal.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.Experience;
import com.jobportal.dto.ProfileDTO;
import com.jobportal.entity.Profile;
import com.jobportal.exception.JobPortalException;
import com.jobportal.repository.ProfileRepository;
import com.jobportal.utility.Utilities;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Long createProfile(String email, String name) throws JobPortalException {
		Profile profile = new Profile();
		profile.setId(Utilities.getNextSequence("profiles"));
		profile.setEmail(email);
		profile.setSkills(new ArrayList<>());
		profile.setExperiences(new ArrayList<>());
		profile.setCertifications(new ArrayList<>());
		profile.setSavedJobs(new ArrayList<>());
		profile.setName(name);
		profileRepository.save(profile);
		return profile.getId();
	}

	@Override
	public ProfileDTO getProfile(Long id) throws JobPortalException {
	    Profile profile = profileRepository.findById(id)
	        .orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));

	    List<Experience> experiences = profile.getExperiences();
	    if (experiences != null && !experiences.isEmpty()) {
	        boolean updated = false;
	        for (Experience exp : experiences) {
	            if (Boolean.TRUE.equals(exp.getWorking()) &&
	                exp.getEndDate() != null &&
	                !exp.getEndDate().toLocalDate().isEqual(LocalDate.now())) {

	                exp.setEndDate(LocalDateTime.now());
	                updated = true;
	            }
	        }

	        if (updated) {
	            profile.setTotalExp(calculateTotalExp(profile.toDTO()));
	            profileRepository.save(profile);
	        }
	    }

	    return profile.toDTO();
	}


	@Override
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
		profileRepository.findById(profileDTO.getId()).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));
        getWorkingIsTrueSetEndDate(profileDTO.getId(),profileDTO);
        profileDTO.setTotalExp(calculateTotalExp(profileDTO));
        profileRepository.save(profileDTO.toEntity());
        return profileDTO;
	}

	@Override
	public List<ProfileDTO> getAllProfile() {
		return profileRepository.findAll().stream().map((x)-> x.toDTO()).toList();
	}
	
	public void getWorkingIsTrueSetEndDate(Long id, ProfileDTO profileDTO) throws JobPortalException {
        // only returns updated experience, but it doesnot updates in the backend
        profileRepository.findById(id).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));
        // if any updates done, then previous experience, endDate of working = present should update here
        profileRepository.findById(id).ifPresent(profile1 -> profile1.getExperiences().stream().filter(experience -> (experience.getWorking() == true) && (experience.getEndDate() != LocalDateTime.now())).forEach(experience -> {
        	profileDTO.getExperiences().stream().filter(experience1 -> experience1.getWorking()==true).forEach(experience1 -> experience1.setEndDate(LocalDateTime.now()));
//            System.out.println(profileDto.toString());

        }));
    }

    public Long calculateTotalExp(ProfileDTO profileDTO){
//        profileDto.getExperiences().stream().forEach(System.out::println);
        List<Long> months = profileDTO.getExperiences().stream().map(experience -> ChronoUnit.MONTHS.between(experience.getStartDate(), experience.getEndDate())).collect(Collectors.toList());
        Long sum = months.stream().mapToLong(Long::longValue).sum();
        double expYear = Math.round(sum/12);
        return (long) expYear;
    }

}










