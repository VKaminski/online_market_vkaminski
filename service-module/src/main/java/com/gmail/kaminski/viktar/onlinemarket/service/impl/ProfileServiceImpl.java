package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ProfileRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ProfileConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
    private static final Marker custom = MarkerFactory.getMarker("custom");
    private ProfileRepository profileRepository;
    private ProfileConverter profileConverter;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileConverter profileConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
    }

    @Override
    @Transactional
    public ProfileDTO getById(Long id) {
        Profile profile = profileRepository.findById(id);
        return profileConverter.toProfileDTO(profile);
    }
}
