package com.tzuchaedahy.service;

import java.util.List;

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

    public Product searchProductById(Integer id) {
        return productDao.findById(id);
    }

    public List<Product> listAllAvailableProducts() {
        return productDao.listAllAvailableProducts();
    }
}
