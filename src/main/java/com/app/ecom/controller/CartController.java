package com.app.ecom.controller;


import com.app.ecom.dto.CartItemsRequest;
import com.app.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String UserId,
            @RequestBody CartItemsRequest cartItemsRequest
    ){
        Boolean cartItem = cartService.addToCart(UserId, cartItemsRequest);
        if(cartItem){
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return  ResponseEntity.badRequest().body("Product out of stock or User not found.");
    }
}
