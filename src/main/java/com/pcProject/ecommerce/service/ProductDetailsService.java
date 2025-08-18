package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.ProductDetails;
import com.pcProject.ecommerce.repository.ProductDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailsRepo productRepo;

    // To get the Product Details using Product name
    public Object getProductDetails(String productName){
        ProductDetails existingProduct = productRepo.findByProductName(productName);
        if(existingProduct == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(existingProduct, HttpStatus.OK);
    }

    // To add a Product
    public Object addProductDetails(ProductDetails product){
        productRepo.save(product);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    //To update an existing Product based on Product name
    public Object updateProductDetails(ProductDetails product){
        ProductDetails existingProduct = productRepo.findByProductName(product.getProductName());
        if(existingProduct == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        existingProduct.setProductValue(product.getProductValue());
        return new ResponseEntity<>(existingProduct,HttpStatus.OK);
    }

    //To delete an existing Product based on Product name
    public Object deleteProductDetails(String productName){
        ProductDetails existingProduct = productRepo.findByProductName(productName);
        if(existingProduct == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        productRepo.deleteByProductName(productName);
        return new ResponseEntity<>("Deleted the Product Details successfully", HttpStatus.OK);
    }
}
