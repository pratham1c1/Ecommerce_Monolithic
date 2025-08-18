package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.UserDetails;
import com.pcProject.ecommerce.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepo userRepo;

    public Object getUserProfile(String userName){
        UserDetails user =  userRepo.findByUserName(userName);
        if(user == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    public Object addUserProfile(UserDetails user){
        userRepo.save(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    public Object updateUserProfile(UserDetails user){
        UserDetails existingUser = userRepo.findByUserName(user.getUserName());
        if(existingUser == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        existingUser.setUserEmail(user.getUserEmail());
        existingUser.setUserPassword(user.getUserPassword());
        existingUser.setUserMobileNumber(user.getUserMobileNumber());
        userRepo.save(existingUser);
        return new ResponseEntity<>(existingUser,HttpStatus.OK);
    }

    public Object deleteUserProfile(String username){
        UserDetails existingUser = userRepo.findByUserName(username);
        if(existingUser == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        userRepo.deleteByUserName(username);
        return new ResponseEntity<>("User Profile deleted successfully", HttpStatus.OK);
    }
}