package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.ProductDetails;
import com.pcProject.ecommerce.model.UserDetails;
import com.pcProject.ecommerce.repository.ProductDetailsRepo;
import com.pcProject.ecommerce.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private UserDetailsRepo userRepo;
    @Autowired
    private ProductDetailsRepo productRepo;

    public Object getAllOrderDetails(String userName){
        UserDetails existingUser = userRepo.findByUserName(userName);
        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(existingUser.getUserProductIds(),HttpStatus.OK);
    }

    public Object addOrderDetails(String userName, String productName){
        UserDetails existingUser = userRepo.findByUserName(userName);
        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        ProductDetails existingProduct = productRepo.findByProductName(productName);
        if(existingProduct == null)
            return new ResponseEntity<>("Could not found the given product",HttpStatus.BAD_REQUEST);

        List<ProductDetails> orderedProducts = existingUser.getUserProductIds();
        orderedProducts.add(existingProduct);
        existingUser.setUserProductIds(orderedProducts);

        userRepo.save(existingUser);

        return new ResponseEntity<>(existingUser,HttpStatus.OK);
    }

    public Object deleteOrderDetails(String userName, String productName){
        UserDetails existingUser = userRepo.findByUserName(userName);
        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        ProductDetails existingProduct = productRepo.findByProductName(productName);
        if(existingProduct == null)
            return new ResponseEntity<>("Could not found the given product",HttpStatus.BAD_REQUEST);

        List<ProductDetails> orderedProducts = existingUser.getUserProductIds();
        if(orderedProducts.contains(existingProduct)){
            orderedProducts.remove(existingProduct);
            existingUser.setUserProductIds(orderedProducts);
            userRepo.save(existingUser);
            return new ResponseEntity<>("Deleted the Order successfully" , HttpStatus.OK);
        }
        else
            new ResponseEntity<>("Don't have Order for this Product" , HttpStatus.OK);

        return new ResponseEntity<>("Something went wrong !" , HttpStatus.BAD_GATEWAY);
    }
}