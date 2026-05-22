package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
}
