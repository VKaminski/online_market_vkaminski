package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ProfileRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.entity.Profile;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl extends GenericRepositoryImpl<Long, Profile> implements ProfileRepository {


}
