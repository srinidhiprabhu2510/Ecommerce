package com.app.ecom.service.serviceImpl;

import com.app.ecom.Enum.OrderStatus;
import com.app.ecom.dto.OrderItemsDto;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.CartItems;
import com.app.ecom.model.OrderItems;
import com.app.ecom.model.Orders;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemsRepository;
import com.app.ecom.repository.OrdersRepository;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.CartService;
import com.app.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartItemsRepository cartItemsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Optional<OrderResponse> createOrder(String userId) {
        //validate cart of user
        List<CartItems> cartItems = cartService.getCartItems(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        //validate user
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return Optional.empty();
        }
        User user = userOpt.get();
        //calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItems::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //create order

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        orders.setTotalAmount(totalPrice);
        List<OrderItems> orderItems = cartItems.stream()
                .map(item ->new OrderItems(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        orders
                )).toList();
        orders.setItems(orderItems);
        Orders savedOrder = ordersRepository.save(orders);

        //clear cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Orders orders) {
        return new OrderResponse(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getOrderStatus(),
                orders.getItems().stream()
                        .map(orderItems -> new OrderItemsDto(
                                orderItems.getId(),
                                orderItems.getProduct().getId(),
                                orderItems.getQuantity(),
                                orderItems.getPrice(),
                                orderItems.getPrice().multiply(BigDecimal.valueOf(orderItems.getQuantity()))
                        ))
                        .toList(),
                orders.getCreatedAt()
        );
    }
}
