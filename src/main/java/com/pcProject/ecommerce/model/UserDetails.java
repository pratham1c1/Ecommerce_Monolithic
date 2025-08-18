package com.pcProject.ecommerce.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true)
    private String userName;
    private String userPassword;
    private String userEmail;
    private long userMobileNumber;
    @ManyToMany
    private List<ProductDetails> userProductIds;

}