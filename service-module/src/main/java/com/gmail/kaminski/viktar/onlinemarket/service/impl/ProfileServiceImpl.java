package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ProfileRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.User;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ProfileConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Value("${custom.password.crypto.round}")
    private int cryptoRound;
    private ProfileRepository profileRepository;
    private ProfileConverter profileConverter;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              ProfileConverter profileConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
    }

    @Override
    @Transactional
    public ProfileDTO getById(Long id) {
        Profile profile = profileRepository.findById(id);
        return profileConverter.toProfileDTO(profile);
    }

    @Override
    @Transactional
    public void update(ProfileDTO profileDTO) {
        UserDTO updated = profileDTO.getUser();
        Profile profile = profileRepository.findById(updated.getId());
        String hashPassword = BCrypt.hashpw(updated.getPassword(), BCrypt.gensalt(cryptoRound));
        User user = profile.getUser();
        user.setName(updated.getName());
        user.setSurname(updated.getSurname());
        user.setPassword(hashPassword);
        profile.setUser(user);
        profile.setAddress(profileDTO.getAddress());
        profile.setPhone(profileDTO.getPhone());
        profileRepository.update(profile);
    }
}
