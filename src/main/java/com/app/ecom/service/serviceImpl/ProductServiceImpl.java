package com.app.ecom.service.serviceImpl;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product product1 = productRepository.save(product);
        return mapTosavedProduct(product1);
    }

    private ProductResponse mapTosavedProduct(Product product1) {
        ProductResponse response = new ProductResponse();
        response.setId(product1.getId());
        response.setName(product1.getName());
        response.setActive(product1.getActive());
        response.setCategory(product1.getCategory());
        response.setDescription(product1.getDescription());
        response.setStockQuantity(product1.getStockQuantity());
        response.setPrice(product1.getPrice());
        response.setImageUrl(product1.getImageUrl());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setId(product.getId());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setActive(productRequest.getActive());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());

    }

}
