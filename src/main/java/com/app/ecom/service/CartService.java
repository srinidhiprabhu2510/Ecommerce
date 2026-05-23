package com.app.ecom.service;

import com.app.ecom.dto.CartItemsRequest;

public interface CartService {

    boolean addToCart(String userId, CartItemsRequest cartItemsRequest);
}
