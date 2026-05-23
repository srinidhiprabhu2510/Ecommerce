package com.app.ecom.dto;

import lombok.Data;

@Data
public class CartItemsRequest {
    private Long productId;
    private Integer quantity;
}
