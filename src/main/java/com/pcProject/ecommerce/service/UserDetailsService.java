package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.*;
import com.pcProject.ecommerce.repository.ProductDetailsRepo;
import com.pcProject.ecommerce.repository.UserDetailsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepo userRepo;
    @Autowired
    private ProductDetailsRepo productRepo;

    public Object getUserProfile(String userName){
        UserDetails user =  userRepo.findByUserName(userName);
        if(user == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        UserProductWrap userProductWrap = new UserProductWrap();
        userProductWrap.mapUser(user);

        return new ResponseEntity<>(userProductWrap,HttpStatus.OK);
    }

    public Object getAllUsers() {
        List<UserDetails> allUsers = userRepo.findAll();
        List<UserProductWrap> allUserWrap = new ArrayList<>();

        for(UserDetails user : allUsers){
            UserProductWrap userWrap = new UserProductWrap();
            userWrap.mapUser(user);
            allUserWrap.add(userWrap);
        }
        return new ResponseEntity<>(allUserWrap,HttpStatus.OK);
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

    public Object getAllUserProducts(String userName) {
        UserDetails existingUser = userRepo.findByUserName(userName);
        List<ProductWrapper> userProducts = new ArrayList<>();

        if(existingUser == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        for(ProductDetails product : existingUser.getUserProductIds())
            userProducts.add(new ProductWrapper(product.getProductId(),product.getProductName()));

        return new ResponseEntity<>(userProducts,HttpStatus.OK);
    }

    @Transactional
    public Object removeUserProduct(UserProductNameWrap userProduct) {
        UserDetails existingUser = userRepo.findByUserName(userProduct.getUserName());
        if(existingUser == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        ProductDetails orderedProduct = productRepo.findByProductName(userProduct.getProductName());

        if(!existingUser.getUserProductIds().contains(orderedProduct))
            return new ResponseEntity<>("User don't have any order for the mentioned product",HttpStatus.BAD_REQUEST);
        List<ProductDetails> userProductList = existingUser.getUserProductIds();
        userProductList.remove(orderedProduct);
        existingUser.setUserProductIds(userProductList);
        return new ResponseEntity<>(existingUser,HttpStatus.OK);
    }
}