package com.app.ecom.service;

import com.app.ecom.dto.CartItemsRequest;
import com.app.ecom.model.CartItems;

import java.util.List;

public interface CartService {

    boolean addToCart(String userId, CartItemsRequest cartItemsRequest);

    Boolean deleteItemFromCart(String userId, Long productId);

    List<CartItems> getCartItems(String userId);
}
