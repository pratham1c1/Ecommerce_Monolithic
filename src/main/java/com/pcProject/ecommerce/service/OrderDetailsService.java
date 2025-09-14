package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.*;
import com.pcProject.ecommerce.repository.ProductDetailsRepo;
import com.pcProject.ecommerce.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private UserDetailsRepo userRepo;
    @Autowired
    private ProductDetailsRepo productRepo;
    @Autowired
    private ProductDetailsService productDetailsService;

    public Object getAllOrderDetails(String userName){
        UserDetails existingUser = userRepo.findByUserName(userName);
        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        List<ProductWrapper> allProductWrap = new ArrayList<>();
        for(ProductDetails product : existingUser.getUserProductIds())
            allProductWrap.add(new ProductWrapper(product.getProductId(),product.getProductName()));

        return new ResponseEntity<>(allProductWrap,HttpStatus.OK);
    }

    public Object addOrderDetails(String userName, String productName){
        UserDetails existingUser = userRepo.findByUserName(userName);
        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        ProductDetails existingProduct = productRepo.findByProductName(productName);
        if(existingProduct == null)
            return new ResponseEntity<>("Could not found the given product",HttpStatus.BAD_REQUEST);
        else if(existingProduct.getProductQuantity() == 0)
            return new ResponseEntity<>("Product is not available at the movement !", HttpStatus.BAD_REQUEST);


        List<ProductDetails> orderedProducts = existingUser.getUserProductIds();
        orderedProducts.add(existingProduct);
        existingUser.setUserProductIds(orderedProducts);

        userRepo.save(existingUser);

        //To consume the product
        productDetailsService.consumeProduct(productName);

        UserProductWrap existingUserWrap = new UserProductWrap();
        existingUserWrap.mapUser(existingUser);
        return new ResponseEntity<>(existingUserWrap,HttpStatus.OK);
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
            productDetailsService.addToProductQuantity(new ProductQuantityWrap(productName,1));
            return new ResponseEntity<>("Deleted the Order successfully" , HttpStatus.OK);
        }
        else
            new ResponseEntity<>("Don't have Order for this Product" , HttpStatus.OK);

        return new ResponseEntity<>("Something went wrong !" , HttpStatus.BAD_GATEWAY);
    }
}