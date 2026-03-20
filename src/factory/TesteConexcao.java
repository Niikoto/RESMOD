package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexcao {
    static void main(String[] args) throws SQLException {
        Connection conection = ConnectionFactory.getConnection();
        System.out.println("CONEXÃO FUNCIONANDO!");
        conection.close();
    }
}
