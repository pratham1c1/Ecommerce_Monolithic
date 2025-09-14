package com.pcProject.ecommerce.service;

import com.pcProject.ecommerce.model.*;
import com.pcProject.ecommerce.repository.OrderDetailsRepo;
import com.pcProject.ecommerce.repository.ProductDetailsRepo;
import com.pcProject.ecommerce.repository.UserDetailsRepo;
import org.hibernate.query.Order;
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
    @Autowired
    private OrderDetailsRepo orderRepo;

    public Object getAllOrderDetails(String userName){
        UserDetails existingUser = userRepo.findByUserName(userName);

        if(existingUser == null)
            return new ResponseEntity<>("Could not found the given user" , HttpStatus.BAD_REQUEST);

        List<OrderDetails> userOrders = orderRepo.findAllByUserName(userName);
        List<ProductOrderWrap> userProducts = new ArrayList<>();

        for(OrderDetails order : userOrders){
            userProducts.add(new ProductOrderWrap(order.getOrderId(),order.getProductName(),order.getOrderStatus()));
        }

        return new ResponseEntity<>(userProducts,HttpStatus.OK);
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

        //To add the order
        OrderDetails userOrder = new OrderDetails();
        userOrder.setUserName(userName);
        userOrder.setProductName(productName);
        orderRepo.save(userOrder);

        //To return with Status
        ProductOrderWrap userProduct = new ProductOrderWrap(userOrder.getOrderId(),productName,userOrder
                .getOrderStatus());

        UserProductWrap existingUserWrap = new UserProductWrap();
        existingUserWrap.mapUser(existingUser);
        return new ResponseEntity<>(userProduct,HttpStatus.OK);
    }

    public Object updateOrderDetails(String userName, String productName){
        List<OrderDetails> firstUserOrder = orderRepo.findAllByUserNameAndProductName(userName,productName);
        if(firstUserOrder.size() == 0){
            return new ResponseEntity<>("User don't have orde for the given product !", HttpStatus.BAD_REQUEST);
        }
        OrderDetails userOrder = firstUserOrder.get(0);
        if(userOrder.getOrderStatus().equals("Placed")){
            userOrder.setOrderStatus("Shipped");
            orderRepo.save(userOrder);
            return new ResponseEntity<>("Updated the Status successfully !" , HttpStatus.OK);
        }
        else if(userOrder.getOrderStatus().equals("Shipped")){
            userOrder.setOrderStatus("Delivered");
            orderRepo.delete(userOrder);
            return new ResponseEntity<>("Order Delivered Successfully !", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong !", HttpStatus.BAD_GATEWAY);
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

            //To Delete from Order
            List<OrderDetails> userOrders = orderRepo.findAllByUserNameAndProductName(userName,productName);
            orderRepo.delete(userOrders.get(0));

            return new ResponseEntity<>("Deleted the Order successfully" , HttpStatus.OK);
        }

        return new ResponseEntity<>("Don't have Order for this Product" , HttpStatus.OK);
    }
}