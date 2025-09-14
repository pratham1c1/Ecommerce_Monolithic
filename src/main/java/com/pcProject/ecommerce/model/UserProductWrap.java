package com.pcProject.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProductWrap {
    private String userName;
    private String userPassword;
    private String userEmail;
    private long userMobileNumber;
    private List<ProductWrapper> userProducts;

    public void mapUser(UserDetails user) {
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userMobileNumber = user.getUserMobileNumber();
        this.userProducts = new ArrayList<>();
        for(ProductDetails product : user.getUserProductIds())
            userProducts.add(new ProductWrapper(product.getProductId(),product.getProductName()));
    }
}
