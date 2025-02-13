package com.tzuchaedahy.service;

import com.tzuchaedahy.domain.Product;
import com.tzuchaedahy.repository.ProductDao;

public class ProductService {
    private ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public void registerProduct(Product product) {
        this.productDao.save(product);
    }
}
