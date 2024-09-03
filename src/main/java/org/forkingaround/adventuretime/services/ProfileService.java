package org.forkingaround.adventuretime.services;

import java.util.Optional;

import org.forkingaround.adventuretime.models.Profile;
import org.forkingaround.adventuretime.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    public String getEmailByUserId(Long userId) {
        Optional<Profile> profileOptional = profileRepository.findById(userId);
        if (profileOptional.isPresent()) {
            return profileOptional.get().getEmail();
        }

        return null;
    }
        

}