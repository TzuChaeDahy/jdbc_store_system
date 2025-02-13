# jdbc_store_system

Este é um projeto Java para um sistema de loja utilizando JDBC para conexão com o banco de dados MySQL.

## Estrutura do Projeto

O projeto possui a seguinte estrutura de diretórios:

```

.vscode/
docker-compose.yml
init.sql
pom.xml
src/
main/
java/
com/
tzuchaedahy/
application/
db/
domain/
repository/
service/
utils/
Main.java
target/

```

## Configuração do Banco de Dados

O projeto utiliza um banco de dados MySQL. Para configurar o banco de dados, você pode usar o arquivo [`docker-compose.yml`](docker-compose.yml) para iniciar um contêiner MySQL com as configurações necessárias. O arquivo [`init.sql`](init.sql) contém os scripts SQL para criar as tabelas e inserir dados iniciais.

### Passos para Configuração

1. Certifique-se de ter o Docker instalado em sua máquina.
2. Execute o comando abaixo para iniciar o contêiner MySQL:
   ```sh
   docker-compose up -d
   ```
3. O banco de dados será criado e populado automaticamente com os dados do arquivo [`init.sql`](init.sql).

### Alterar Configurações de Conexão

Lembre-se de alterar o arquivo [`DbConnection`](src/main/java/com/tzuchaedahy/db/DbConnection.java) para garantir que as configurações de conexão com o banco de dados estejam corretas para o seu ambiente:

```java
package com.tzuchaedahy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";

        final String url = "jdbc:mysql://localhostLOJA";
        final String user = "root";
        final String password = "test@123";

        Class.forName(driver);

        return DriverManager.getConnection(url, user, password);
    }
}
```

## Compilação e Execução

Para compilar e executar o projeto, siga os passos abaixo:

1. Certifique-se de ter o Maven instalado em sua máquina.
2. Navegue até o diretório do projeto e execute o comando abaixo para compilar o projeto:
   ```sh
   mvn clean install
   ```
3. Após a compilação, execute o comando abaixo para iniciar a aplicação:
   ```sh
   java -jar target/jdbc_store_system-1.0-SNAPSHOT.jar
   ```

## Funcionalidades

O sistema possui as seguintes funcionalidades:

1. Cadastro de produtos
2. Cadastro de clientes
3. Busca de produtos por ID
4. Listagem de produtos disponíveis
5. Efetuar vendas
6. Listagem de vendas realizadas
