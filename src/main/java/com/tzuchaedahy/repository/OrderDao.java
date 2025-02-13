package com.tzuchaedahy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.tzuchaedahy.db.DbConnection;
import com.tzuchaedahy.domain.Client;
import com.tzuchaedahy.domain.Employee;
import com.tzuchaedahy.domain.OrderProduct;

public class OrderDao {
    public void save(Client cliente, Employee employee, List<OrderProduct> products) {
        try {
            Connection conn = DbConnection.getConnection();

            String orderQuery = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?);";
            PreparedStatement orderStatement = conn.prepareStatement(orderQuery);

            Double valorTotal = products.stream().map(product -> product.getPrice()).reduce(0.0, (a, b) -> a + b);

            orderStatement.setString(1, cliente.getCpf());
            orderStatement.setString(2, employee.getCpf());
            orderStatement.setDouble(3, valorTotal);

            orderStatement.execute();

            orderStatement.close();

            products.forEach(product -> {
                try {
                    String orderProductQuery = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK ,QUANTIDADE, VALOR) VALUES (?, ?, ?, ?);";
                    PreparedStatement productStatement = conn.prepareStatement(orderProductQuery);

                    productStatement.setInt(1, product.getOrderId());
                    productStatement.setInt(2, product.getProductId());
                    productStatement.setInt(3, product.getQuantity());
                    productStatement.setDouble(4, product.getPrice());

                    productStatement.execute();

                    productStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
