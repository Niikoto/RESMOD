package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import factory.ConnectionFactory;
import modelo.Produto;

public class ProdutoDAO {
    Connection connetion = ConnectionFactory.getConnection();

    public void cadastrarProduto(Produto p) throws SQLException {
        String sql = "insert into produto (nome_produto,preco,quantidade,minimo,COD_categoria,COD_CNPJ) values (?,?,?,?,?,?)";

        try (PreparedStatement comando = connetion.prepareStatement(sql)) {
            comando.setString(1, p.getNome_produto());
            comando.setFloat(2, p.getPreco());
            comando.setInt(3, p.getQuantidade());
            comando.setInt(4, p.getMinimo());
            comando.setInt(5, p.getCOD_categoria());
            comando.setString(6, p.getCOD_CNPJ());

            comando.executeUpdate();
        }
    }
}
