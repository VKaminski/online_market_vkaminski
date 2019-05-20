package com.gmail.kaminski.viktar.onlinemarket.service;

import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;

public interface ProfileService {
    ProfileDTO getByUserEmail(String email);
}
