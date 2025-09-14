package com.pcProject.ecommerce.controller;

import com.pcProject.ecommerce.model.UserDetails;
import com.pcProject.ecommerce.model.UserProductNameWrap;
import com.pcProject.ecommerce.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("getAllUsers")
    public Object getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("getAllUserProducts/{userName}")
    public Object getAllUserProducts(@PathVariable String userName){
        return userService.getAllUserProducts(userName);
    }

    @PostMapping("addUserProfile")
    public Object addUserProfile(@RequestBody UserDetails user){
        return userService.addUserProfile(user);
    }

    @PutMapping("updateUserProfile")
    public Object updateUserProfile(@RequestBody UserDetails user){
        return userService.updateUserProfile(user);
    }

    @PutMapping("removeUserProduct")
    public Object removeUserProduct(@RequestBody UserProductNameWrap userProduct){
        return userService.removeUserProduct(userProduct);
    }

    @DeleteMapping("deleteUserProfile/{username}")
    public Object deleteUserProfile(@PathVariable String username){
        return userService.deleteUserProfile(username);
    }
}
