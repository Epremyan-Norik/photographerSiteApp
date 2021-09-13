package com.photographer.app.mapper;

import com.photographer.app.model.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> getAllProducts();
    Product getProductById(long id);
}
