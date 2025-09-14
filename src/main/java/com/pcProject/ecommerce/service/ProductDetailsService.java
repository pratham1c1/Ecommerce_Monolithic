package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.ProductDetails;
import com.pcProject.ecommerce.model.ProductQuantityWrap;
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

    //To get All Products
    public Object getAllProductDetails(){
        return productRepo.findAll();
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
        productRepo.save(existingProduct);
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

    //To add into product quanatity
    public Object addToProductQuantity(ProductQuantityWrap productQuantityWrap){
        ProductDetails existingProduct = productRepo.findByProductName(productQuantityWrap.getProductName());
        if(existingProduct == null)
            return new ResponseEntity<>("Could not find the Product with given name",HttpStatus.BAD_REQUEST);

        existingProduct.setProductQuantity(existingProduct.getProductQuantity()+productQuantityWrap.getProductQuantity());
        productRepo.save(existingProduct);
        return new ResponseEntity<>("Added to Product Quantity successfully !",HttpStatus.OK);
    }

    //To consume the Product
    public Object consumeProduct(String productName){
        ProductDetails existingProduct = productRepo.findByProductName(productName);
        if(existingProduct == null){
            return new ResponseEntity<>("Don't have product with the given name",HttpStatus.BAD_REQUEST);
        }
        existingProduct.setProductQuantity(existingProduct.getProductQuantity()-1);
        productRepo.save(existingProduct);
        return new ResponseEntity<>("Successfully consumed the Product",HttpStatus.OK);
    }
}
