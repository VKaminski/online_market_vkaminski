package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileService {
    ProfileDTO getById(Long id);

    void update(ProfileDTO profileDTO);
}
