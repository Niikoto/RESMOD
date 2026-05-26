package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Historico_Pedido_DAO {
    Connection conectar = ConnectionFactory.getConnection();

    public int cadastrarHistoricoPedido(int cod_pedido, String status, String cod_email) throws SQLException {
        String sql = "INSERT INTO historico_pedido(COD_email, `status`, data_alteracao, `COD_pedido`) VALUES(?, ?, NOW(), ?);";

        int idHisPed = 0;

        try (PreparedStatement comando = conectar.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, cod_email);
            comando.setString(2, status);
            comando.setInt(3, cod_pedido);

            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                return idHisPed = resultado.getInt(1);
            }

            return idHisPed;
        }
    }
}


