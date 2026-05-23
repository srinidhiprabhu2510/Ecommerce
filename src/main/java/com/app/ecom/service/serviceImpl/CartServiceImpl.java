package com.app.ecom.service.serviceImpl;

import com.app.ecom.dto.CartItemsRequest;
import com.app.ecom.model.CartItems;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemsRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartServiceImpl  implements CartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemsRepository cartItemsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean addToCart(String userId, CartItemsRequest cartItemsRequest) {
        //Look for the product
        Optional<Product> product = productRepository.findById(cartItemsRequest.getProductId());
        if(product.isEmpty()){
            return false;
        }

        Product product1 = product.get();
        if(product1.getStockQuantity() < cartItemsRequest.getQuantity()){
            return false;
        }

        if(product1.getActive() != true ){
            return false;
        }

        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()){
            return false;
        }

        User user1 = user.get();
        //check if the cart exist and product exist in cart. If yes increase the quantity. else creae a cart with product.
        CartItems existingcartItems = cartItemsRepository.findByUserAndProduct(user1, product1);

        if(existingcartItems != null){
            //update quantity
            existingcartItems.setQuantity(existingcartItems.getQuantity() + cartItemsRequest.getQuantity());
            existingcartItems.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(existingcartItems.getQuantity())));
            cartItemsRepository.save(existingcartItems);
        }
        else{
            //Create new cart item
            CartItems cartItems = new CartItems();
            cartItems.setUser(user1);
            cartItems.setProduct(product1);
            cartItems.setQuantity(cartItemsRequest.getQuantity());
            cartItems.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(cartItemsRequest.getQuantity())));
            cartItemsRepository.save(cartItems);
        }
        return true;
    }
}
