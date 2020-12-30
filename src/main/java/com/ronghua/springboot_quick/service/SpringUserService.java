package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.entity.app_user.AppUser;
import com.ronghua.springboot_quick.entity.app_user.UserAuthority;
import com.ronghua.springboot_quick.entity.auth.SpringUser;
import com.ronghua.springboot_quick.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpringUserService implements UserDetailsService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser user = appUserService.getUserByEmail(username);
//            List<SimpleGrantedAuthority> authorities = convertToSimpleAuthorities(user.getAuthorities());
////            return new User(user.getEmailAddress(), user.getPassword(), Collections.emptyList());
//            return new User(user.getEmailAddress(), user.getPassword(), authorities);
            return new SpringUser(user);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("Username is wrong.");
        }
    }

    private List<SimpleGrantedAuthority> convertToSimpleAuthorities(List<UserAuthority> authorities) {
        return authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name())) //apply this function to each element in stream and return a new stream contains the new elements
                .collect(Collectors.toList());
    }


}
