package com.tzuchaedahy.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tzuchaedahy.db.DbConnection;
import com.tzuchaedahy.domain.Client;

public class ClientDao {
    public void save(Client client) throws RuntimeException {
        try {

            String query = "INSERT INTO CLIENTE (CPF, NOME, ENDERECO, TELEFONE) VALUES (?, ?, ?, ?);";
            Connection conn = DbConnection.getConnection();

            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, client.getCpf());
            statement.setString(2, client.getName());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getPhone());

            statement.execute();

            statement.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente no banco de dados");
        }
    }
}
