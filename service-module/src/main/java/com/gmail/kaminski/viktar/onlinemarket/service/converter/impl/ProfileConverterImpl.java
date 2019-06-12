package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Profile;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ProfileConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {
    private UserConverter userConverter;

    public ProfileConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public ProfileDTO toProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        if (profile.getUser() != null) {
            profileDTO.setUser(userConverter.toUserDTO(profile.getUser()));
        }
        if (profile.getAddress() != null) {
            profileDTO.setAddress(profile.getAddress());
        }
        if (profile.getPhone() != null) {
            profileDTO.setPhone(profile.getPhone());
        }
        return profileDTO;
    }

    @Override
    public Profile toProfile(ProfileDTO profileDTO) {

        return null;
    }
}
