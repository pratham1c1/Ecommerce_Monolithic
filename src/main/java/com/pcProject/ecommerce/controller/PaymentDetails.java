package com.pcProject.ecommerce.controller;

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

    @PostMapping("settleAllPayment/{userName}")
    public Object settleAllPayment(@PathVariable String userName){
        return paymentService.settleAllPayment(userName);
    }
}
