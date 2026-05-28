package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Entrada_saida;

public class Entrada_SaidaDAO {
    Connection conexao = ConnectionFactory.getConnection();

    public void inserirEouS(Entrada_saida es, String cod_email){
        String sql = "insert entrada_saida(tipo, quantidade, COD_produto, COD_email, data_alteracao) values(?, ? ,? , ?, NOW());";

        try(PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setBoolean(1, es.isTipo());
            comando.setInt(2, es.getQuantidade());
            comando.setInt(3, es.getCOD_produto());
            comando.setString(4, cod_email);

            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Entrada_saida> listar() {

        List<Entrada_saida> lista = new ArrayList<>();

        String sql = "SELECT * FROM entrada_saida";

        try {

            Connection connection = new ConnectionFactory().getConnection();

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Entrada_saida es = new Entrada_saida();

                es.setCOD_produto(rs.getInt("COD_produto"));
                es.setTipo(rs.getBoolean("tipo"));
                es.setQuantidade(rs.getInt("quantidade"));
                es.setData(rs.getDate("data_alteracao"));
                es.setEmail(rs.getString("COD_email"));

                lista.add(es);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}


