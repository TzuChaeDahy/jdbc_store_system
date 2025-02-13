package com.tzuchaedahy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tzuchaedahy.domain.Order;
import com.tzuchaedahy.domain.OrderProduct;
import com.tzuchaedahy.repository.OrderDao;
import com.tzuchaedahy.repository.ProductDao;

public class OrderService {
    private static OrderDao orderDao = new OrderDao();
    private static ProductDao productDao = new ProductDao();

    public void processSell(String clientCpf, String employeeCpf, Map<Integer, Integer> sellProductsMap) {
        List<OrderProduct> sellProductsList = new ArrayList<>();

        sellProductsMap.forEach((productId, quantity) -> {

            Double productPrice = productDao.findPriceById(productId);

            Double price = productPrice * quantity;

            OrderProduct orderProduct = new OrderProduct(null, null, productId, quantity, price);
            orderProduct.setProductId(productId);
            orderProduct.setQuantity(quantity);

            sellProductsList.add(orderProduct);
        });

        orderDao.save(clientCpf, employeeCpf, sellProductsList);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public List<OrderProduct> getAllOrderProducts(Integer orderId) {
        return orderDao.getAllOrderProducts(orderId);
    }
}
