package dao;

import factory.ConnectionFactory;
import modelo.Entrada_saida;
import modelo.Historico_pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Historico_pedido> listar() {

        List<Historico_pedido> lista = new ArrayList<>();

        String sql = "SELECT * FROM historico_pedido";

        try {

            Connection connection = new ConnectionFactory().getConnection();

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Historico_pedido es = new Historico_pedido();

                es.setCOD_pedido(rs.getInt("COD_pedido"));
                es.setStatus(rs.getString("status"));
                es.setData_alteracao(rs.getDate("data_alteracao"));
                es.setCOD_email(rs.getString("COD_email"));

                lista.add(es);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}


