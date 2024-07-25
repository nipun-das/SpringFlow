package com.nipundas.product_service.service;


import com.nipundas.product_service.dto.ProductRequest;
import com.nipundas.product_service.dto.ProductResponse;
import com.nipundas.product_service.model.Product;
import com.nipundas.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor // generates a constructor that initializes only fields marked as final or @NonNull,
                        // provided they weren't initialized upon declaration
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("PRODUCT CREATED SUCCESSFULLY!");

        return new ProductResponse(product.getId(), product.getName(),product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        // to map list of product object to productresponse object
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(),product.getDescription(), product.getPrice()))
                .toList();
    }
}
