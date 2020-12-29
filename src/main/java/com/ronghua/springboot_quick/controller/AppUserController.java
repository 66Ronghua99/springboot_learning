package com.ronghua.springboot_quick.controller;

import com.ronghua.springboot_quick.entity.app_user.AppUserConverter;
import com.ronghua.springboot_quick.entity.app_user.AppUserRequest;
import com.ronghua.springboot_quick.entity.app_user.AppUserResponse;
import com.ronghua.springboot_quick.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {

    private String token;

    @Autowired
    private AppUserService service;

    @PostMapping
    public ResponseEntity<AppUserResponse> createUser(@Valid @RequestBody AppUserRequest request) {
        AppUserResponse user = service.createUser(request);
        System.out.println("creation finished");
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponse> getUser(@PathVariable("id") String id) {
        AppUserResponse user = service.getUserResponseById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getUsers() {
        List<AppUserResponse> users = service.getUserResponses();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/pass")
    public ResponseEntity<AppUserResponse> getUserByPass(@RequestBody Map<String, String> map){
        AppUserResponse response = service.getUserResponseByEmail(map);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<AppUserResponse> getUserByToken(@RequestBody Map<String, String> request){
        String token = (String) request.get("token");
        AppUserResponse response = service.getUserByToken(token);
        return ResponseEntity.ok(response);
    }
}

