package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.entity.app_user.AppUser;
import com.ronghua.springboot_quick.entity.app_user.AppUserConverter;
import com.ronghua.springboot_quick.entity.app_user.AppUserRequest;
import com.ronghua.springboot_quick.entity.app_user.AppUserResponse;
import com.ronghua.springboot_quick.dao.AppUserDao;
import com.ronghua.springboot_quick.exceptions.ConflictException;
import com.ronghua.springboot_quick.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AppUserService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AppUserDao repository;

    private JWTService jwtService;

    public AppUserService(AppUserDao repository, JWTService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public AppUserResponse createUser(AppUserRequest request) {
        Optional<AppUser> existingUser = repository.findByEmailAddress(request.getEmailAddress());
        if (existingUser.isPresent()) {
            throw new ConflictException("This email address has been used.");
        }

        AppUser user = AppUserConverter.toAppUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = repository.insert(user);
        return AppUserConverter.toAppUserResponse(user);
    }

    public AppUserResponse getUserResponseById(String id) {
        AppUser user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find user."));

        return AppUserConverter.toAppUserResponse(user);
    }

    public AppUser getUserByEmail(String email) {
        return repository.findByEmailAddress(email)
                .orElseThrow(() -> new NotFoundException("Can't find user."));
    }

    public List<AppUserResponse> getUserResponses() {
        List<AppUser> users = repository.findAll();
        return AppUserConverter.toAppUserResponses(users);
    }

    public AppUserResponse getUserByToken(String token){
        Map<String, Object> maps = jwtService.parseToken(token);
        String username = (String) maps.get("username");
        return AppUserConverter.toAppUserResponse(getUserByEmail(username));
    }

}
