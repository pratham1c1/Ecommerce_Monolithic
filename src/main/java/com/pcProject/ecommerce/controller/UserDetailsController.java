package com.pcProject.ecommerce.controller;

import com.pcProject.ecommerce.model.UserDetails;
import com.pcProject.ecommerce.service.UserDetailsService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userService;

    @GetMapping("getUserProfile/{username}")
    public Object getUserProfile(@PathVariable String username){
        return userService.getUserProfile(username);
    }

    @PostMapping("addUserProfile")
    public Object addUserProfile(@RequestBody UserDetails user){
        return userService.addUserProfile(user);
    }

    @PutMapping("updateUserProfile")
    public Object updateUserProfile(@RequestBody UserDetails user){
        return userService.updateUserProfile(user);
    }

    @DeleteMapping("deleteUserProfile/{username}")
    public Object deleteUserProfile(@PathVariable String username){
        return userService.deleteUserProfile(username);
    }
}
