package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import factory.ConnectionFactory;
import modelo.Compra;

public class CompraDAO {
    Connection conexao = ConnectionFactory.getConnection();

    public void finalizarCompraPed(Compra c){
        String sql = "insert into compra(obs_compra, valor_da_compra, anexo_fiscal, data_compra, COD_pedido, COD_email) values(?,?,?,now(),?,?)";

        try(PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, c.getObs());
            comando.setFloat(2, c.getValor_da_compra());
            comando.setString(3, c.getAnexo_fiscal());
            comando.setInt(4, c.getCOD_pedido());
            comando.setString(5, c.getCOD_email());

            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}