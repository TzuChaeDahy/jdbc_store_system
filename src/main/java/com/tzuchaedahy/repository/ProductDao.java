package com.tzuchaedahy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tzuchaedahy.db.DbConnection;
import com.tzuchaedahy.domain.Product;

public class ProductDao {
    public void save(Product product) {
        try {

            String query = "INSERT INTO PRODUTO (NOME, VALOR_UNIT, QUANTIDADE) VALUES (?, ?, ?);";
            Connection conn = DbConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getUnitPrice());
            statement.setInt(3, product.getQuantity());

            statement.execute();

            statement.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao salvar produto no banco de dados");
        }
    }

    public List<Product> listAllAvailableProducts() {
        List<Product> products = new ArrayList<>();

        try {
            String query = "SELECT * FROM PRODUTO P WHERE P.QUANTIDADE > 0;";
            Connection conn = DbConnection.getConnection();
            
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Product product = new Product(
                    result.getInt("ID"),
                    result.getString("NOME"),
                    result.getDouble("VALOR_UNIT"),
                    result.getInt("QUANTIDADE")
                );

                products.add(product);
            }

            statement.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }
}
