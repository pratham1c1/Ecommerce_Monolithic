package com.pcProject.ecommerce.controller;

import com.pcProject.ecommerce.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderService;

    // Get all ordered Product of a user
    @GetMapping("getAllOrderDetails/{userName}")
    public Object getAllOrderDetails(@PathVariable String userName){
        return orderService.getAllOrderDetails(userName);
    }

    // Order a product for a user
    @PostMapping("addOrderDetails/{userName}")
    public Object addOrderDetails(@PathVariable String userName, @RequestBody Map<String,Object> payload){
        String productName = (String) payload.get("productName");
        return orderService.addOrderDetails(userName,productName);
    }


    // Delete an ordered Product for a User
    @DeleteMapping("deleteOrderDetails/{userName}")
    public Object deleteOrderDetails(@PathVariable String userName, @RequestBody Map<String,Object> payload){
        String productName = (String) payload.get("productName");
        return orderService.deleteOrderDetails(userName,productName);
    }
}
