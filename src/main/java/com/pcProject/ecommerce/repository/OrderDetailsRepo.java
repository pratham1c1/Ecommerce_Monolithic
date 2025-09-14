package com.pcProject.ecommerce.repository;

import com.pcProject.ecommerce.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails,Integer> {
    List<OrderDetails> findAllByUserName(String userName);

    List<OrderDetails> findAllByUserNameAndProductName(String userName,String productName);
}
