package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.ProfileRepository;
<<<<<<< HEAD
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ProfileConverter;
=======
import com.gmail.kaminski.viktar.onlinemarket.repository.exception.ProfileRepositoryException;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;
import com.gmail.kaminski.viktar.onlinemarket.service.ProfileService;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.ProfileConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.exception.ProfileServiceException;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
import com.gmail.kaminski.viktar.onlinemarket.service.model.ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;
=======

import java.sql.Connection;
import java.sql.SQLException;
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f

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
<<<<<<< HEAD
    @Transactional
    public ProfileDTO getById(Long id) {
        Profile profile = profileRepository.findById(id);
        return profileConverter.toProfileDTO(profile);
=======
    public ProfileDTO getByUserEmail(String email) {
        try (Connection connection = profileRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Profile profile = profileRepository.getByUserEmail(connection, email);
                ProfileDTO profileDTO = profileConverter.toProfileDTO(profile);
                connection.commit();
                return profileDTO;
            } catch (ProfileRepositoryException e) {
                connection.rollback();
                logger.debug(custom, "Operation getByUserId was rollback");
                throw new ProfileServiceException(e);
            }
        } catch (SQLException e) {
            logger.debug(custom, e.getMessage());
            throw new ProfileServiceException(e);
        }
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
    }
}
