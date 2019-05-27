package com.gmail.kaminski.viktar.onlinemarket.repository;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Profile;

<<<<<<< HEAD
public interface ProfileRepository extends GenericRepository<Long, Profile> {
=======
import java.sql.Connection;

public interface ProfileRepository extends GenericRepository {
    Profile getByUserEmail(Connection connection, String email);
>>>>>>> 666068f8e41815d3241301d06ecd5416f12f1e1f
}
