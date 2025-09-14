package com.pcProject.ecommerce.controller;

import com.pcProject.ecommerce.model.UserProductNameWrap;
import com.pcProject.ecommerce.service.PaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentDetails {

    @Autowired
    private PaymentDetailsService paymentService;

    @GetMapping("totalPayment/{userName}")
    public Object getAllPayment(@PathVariable String userName){
        return paymentService.getAllPayment(userName);
    }

    @PostMapping("settleOnePayment")
    public Object settleOnePayment(@RequestBody UserProductNameWrap userProductNameWrap){
        return paymentService.settleOnePayment(userProductNameWrap.getUserName(),userProductNameWrap.getProductName());
    }

    @PostMapping("settleAllPayment/{userName}")
    public Object settleAllPayment(@PathVariable String userName){
        return paymentService.settleAllPayment(userName);
    }
}
