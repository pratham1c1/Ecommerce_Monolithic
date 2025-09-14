package com.pcProject.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderWrap {
    private int orderId;
    private String productName;
    private String orderStatus;
}
