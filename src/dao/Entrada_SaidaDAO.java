package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import factory.ConnectionFactory;
import modelo.Entrada_saida;

public class Entrada_SaidaDAO {
    Connection conexao = ConnectionFactory.getConnection();

    public void inserirEouS(Entrada_saida es){
        String sql = "insert entrada_saida(tipo, quantidade, COD_produto) values(?, ? ,?);";

        try(PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setBoolean(1, es.isTipo());
            comando.setInt(2, es.getQuantidade());
            comando.setInt(3, es.getCOD_produto());

            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
