package com.tzuchaedahy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tzuchaedahy.db.DbConnection;
import com.tzuchaedahy.domain.Order;
import com.tzuchaedahy.domain.OrderProduct;

public class OrderDao {
    public void save(String clienteCpf, String employeeCpf, List<OrderProduct> products) {
        try {
            Connection conn = DbConnection.getConnection();

            String orderQuery = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?);";
            PreparedStatement orderStatement = conn.prepareStatement(orderQuery,
                    java.sql.Statement.RETURN_GENERATED_KEYS);

            Double valorTotal = products.stream().map(product -> product.getPrice()).reduce(0.0, (a, b) -> a + b);

            orderStatement.setString(1, clienteCpf);
            orderStatement.setString(2, employeeCpf);
            orderStatement.setDouble(3, valorTotal);

            orderStatement.executeUpdate();

            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            final Integer[] orderId = { 0 };
            if (generatedKeys.next()) {
                orderId[0] = generatedKeys.getInt(1);
            }
            generatedKeys.close();

            orderStatement.close();

            products.forEach(product -> {
                try {
                    String orderProductQuery = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?);";
                    PreparedStatement productStatement = conn.prepareStatement(orderProductQuery);

                    productStatement.setInt(1, orderId[0]);
                    productStatement.setInt(2, product.getProductId());
                    productStatement.setInt(3, product.getQuantity());
                    productStatement.setDouble(4, product.getPrice());

                    productStatement.execute();

                    String updateProductQuery = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE ID = ?;";
                    PreparedStatement updateProductStatement = conn.prepareStatement(updateProductQuery);

                    updateProductStatement.setInt(1, product.getQuantity());
                    updateProductStatement.setInt(2, product.getProductId());

                    updateProductStatement.execute();

                    updateProductStatement.close();
                    productStatement.close();
                } catch (SQLException e) {
                    try {
                        String deleteOrderQuery = "DELETE FROM PEDIDO WHERE ID = ?;";

                        PreparedStatement deleteOrderStatement = conn.prepareStatement(deleteOrderQuery);

                        deleteOrderStatement.setInt(1, orderId[0]);

                        deleteOrderStatement.executeUpdate();
                        deleteOrderStatement.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException("Não foi possível apagar o pedido!");
                    }
                    throw new RuntimeException("Não foi possível executar a sua venda!");
                }
            });

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Não foi possível executar a sua venda!");
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try {
            Connection conn = DbConnection.getConnection();
            String orderQuery = "SELECT * FROM PEDIDO;";
            PreparedStatement orderStatement = conn.prepareStatement(orderQuery);
            ResultSet orderResultSet = orderStatement.executeQuery();

            while (orderResultSet.next()) {
                int orderId = orderResultSet.getInt("ID");
                String clienteCpf = orderResultSet.getString("CPF_CLIENTE_FK");
                String employeeCpf = orderResultSet.getString("CPF_FUNCIONARIO_FK");
                double valorTotal = orderResultSet.getDouble("VALOR_TOTAL");

                Order order = new Order(orderId, clienteCpf, employeeCpf, valorTotal);
                orders.add(order);
            }

            orderResultSet.close();
            orderStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Não foi possível recuperar os pedidos!");
        }
        return orders;
    }

    public List<OrderProduct> getAllOrderProducts(Integer orderId) {
        List<OrderProduct> products = new ArrayList<>();

        try {
            Connection conn = DbConnection.getConnection();
            String orderProductQuery = "SELECT * FROM ITEM_PEDIDO WHERE ID_PEDIDO_FK = ?;";
            PreparedStatement orderProductStatement = conn.prepareStatement(orderProductQuery);
            orderProductStatement.setInt(1, orderId);
            ResultSet orderProductResultSet = orderProductStatement.executeQuery();

            while (orderProductResultSet.next()) {
                int id = orderProductResultSet.getInt("ID");
                int productId = orderProductResultSet.getInt("ID_PRODUTO_FK");
                int quantity = orderProductResultSet.getInt("QUANTIDADE");
                double price = orderProductResultSet.getDouble("VALOR");

                OrderProduct product = new OrderProduct(id, orderId, productId, quantity, price);
                products.add(product);
            }

            orderProductResultSet.close();
            orderProductStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Não foi possível recuperar os produtos do pedido!");
        }
        return products;
    }
}
