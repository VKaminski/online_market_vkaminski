package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileEditDTO;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;

public interface ProfileService {
    ProfileDTO getById(Long id);

    ProfileEditDTO getForEditById(Long id);

    void update(ProfileEditDTO editProfileDTO, String password);
}
