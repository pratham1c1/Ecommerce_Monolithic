package com.pcProject.ecommerce.controller;

import com.pcProject.ecommerce.model.ProductDetails;
import com.pcProject.ecommerce.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productService;


    @GetMapping("getProductDetails/{productName}")
    public Object getProductDetails(@PathVariable String productName){
        return productService.getProductDetails(productName);
    }

    @PostMapping("addProductDetails")
    public Object addProductDetails(@RequestBody ProductDetails product){
        return productService.addProductDetails(product);
    }

    @PutMapping("updateProductDetails")
    public Object updateProductDetails(@RequestBody ProductDetails product){
        return productService.updateProductDetails(product);
    }

    @DeleteMapping("deleteProductDetails/{productName}")
    public Object deleteProductDetails(@PathVariable String productName){
        return productService.deleteProductDetails(productName);
    }

}
