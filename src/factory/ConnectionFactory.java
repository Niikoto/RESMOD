package factory;

/* AVISO IMPORTANTE
Para rodar o código, coloque uma senha em
String password = (sua senha do MySQL)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    String url = "jdbc:mysql://localhost/intellidog";
    String root = "root";
    String password = ""; // <--- COLOCAR USA SENHA AQUI

    public Connection getConnection(){
        try{
            return DriverManager.getConnection(url, root, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
