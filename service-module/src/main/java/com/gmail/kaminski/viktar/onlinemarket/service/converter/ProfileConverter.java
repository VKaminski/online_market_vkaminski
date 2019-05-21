package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;

public interface ProfileConverter {
    ProfileDTO toProfileDTO(Profile profile);
}
