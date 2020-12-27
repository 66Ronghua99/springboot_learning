package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.entity.app_user.AppUser;
import com.ronghua.springboot_quick.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SpringUserService implements UserDetailsService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser user = appUserService.getUserByEmail(username);

            return new User(user.getEmailAddress(), user.getPassword(), Collections.emptyList());
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("Username is wrong.");
        }
    }

}
