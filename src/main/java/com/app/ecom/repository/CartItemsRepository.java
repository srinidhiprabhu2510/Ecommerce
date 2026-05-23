package com.app.ecom.repository;

import com.app.ecom.model.CartItems;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    CartItems findByUserAndProduct(User user1, Product product1);
}
