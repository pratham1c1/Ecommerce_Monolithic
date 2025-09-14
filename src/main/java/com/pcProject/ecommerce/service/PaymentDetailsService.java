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
public class PaymentDetailsService {
    @Autowired
    private UserDetailsRepo userRepo;
    @Autowired
    private ProductDetailsRepo productRepo;

    public Object getAllPayment(String userName){
        UserDetails existingUser = userRepo.findByUserName(userName);
        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        List<ProductDetails> orderedProduct = existingUser.getUserProductIds();
        if(orderedProduct.isEmpty())
            return new ResponseEntity<>("You don't have any items in your bucket" , HttpStatus.OK);

        int totalExpense = 0;
        for(ProductDetails product : orderedProduct)
            totalExpense+= product.getProductValue();

        return new ResponseEntity<>(totalExpense , HttpStatus.OK);
    }

    public Object settleOnePayment(String userName,String productName){
        UserDetails user = userRepo.findByUserName(userName);
        if(user == null)
            return new ResponseEntity<>("User is not registered in the system !",HttpStatus.BAD_REQUEST);
        List<ProductDetails> allUserProducts = user.getUserProductIds();
        if(allUserProducts.size() == 0)
            return new ResponseEntity<>("The Order list is empty !", HttpStatus.OK);

        ProductDetails userProduct = productRepo.findByProductName(productName);

        if(allUserProducts.contains(userProduct)){
            allUserProducts.remove(userProduct);
            user.setUserProductIds(allUserProducts);
            userRepo.save(user);
            return new ResponseEntity<>("Successfully completed payment for "+productName , HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong !" , HttpStatus.BAD_GATEWAY);
    }

    public Object settleAllPayment(String userName){
        Object paymentInfo = getAllPayment(userName);
        ResponseEntity<String> res = (ResponseEntity) paymentInfo;

        if(res.getStatusCode().value() == 200) {
            UserDetails existingUser = userRepo.findByUserName(userName);
            List<ProductDetails> orderedProducts = existingUser.getUserProductIds();
            orderedProducts.clear();
            existingUser.setUserProductIds(orderedProducts);
            userRepo.save(existingUser);
            return new ResponseEntity<>("Completed your payment.", HttpStatus.OK);
        }
        else if(res.getStatusCode().value() != 200 || res.getStatusCode().value() == 404)
            return res;

        return new ResponseEntity<>("Something went wrong !" , HttpStatus.BAD_GATEWAY);
    }
}
