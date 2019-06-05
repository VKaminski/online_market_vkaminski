package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ProfileRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Profile;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.User;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ProfileConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileEditDTO;
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
    public ProfileEditDTO getForEditById(Long id) {
        Profile profile = profileRepository.findById(id);
        ProfileEditDTO editProfileDTO = new ProfileEditDTO();
        editProfileDTO.setName(profile.getUser().getName());
        editProfileDTO.setSurname(profile.getUser().getSurname());
        editProfileDTO.setAddress(profile.getAddress());
        editProfileDTO.setPhone(profile.getPhone());
        return editProfileDTO;
    }

    @Override
    @Transactional
    public void update(ProfileEditDTO editProfileDTO, String password) {
        Profile profile = profileRepository.findById(editProfileDTO.getId());
        User user = profile.getUser();
        if (password != null) {
            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(cryptoRound));
            user.setPassword(hashPassword);
        }
        user.setName(editProfileDTO.getName());
        user.setSurname(editProfileDTO.getSurname());
        profile.setUser(user);
        profile.setAddress(editProfileDTO.getAddress());
        profile.setPhone(editProfileDTO.getPhone());
        profileRepository.update(profile);
    }
}
