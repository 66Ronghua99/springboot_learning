package com.ronghua.springboot_quick.dao;

import com.ronghua.springboot_quick.entity.app_user.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDao extends MongoRepository<AppUser, String> {
    @Query(value = "{'emailAddress': '?0'}")
    Optional<AppUser> findByEmailAddress(String email);
}
