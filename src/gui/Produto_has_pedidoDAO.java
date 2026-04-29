package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Produto;
import modelo.Produto_has_pedido;

public class Produto_has_pedidoDAO {
    Connection conectar = ConnectionFactory.getConnection();

    public List<Produto_has_pedido> listarProdutosPedidos(int pp) {
        List<Produto_has_pedido> listaProdutoPedido = new ArrayList<>();
        ResultSet resultado = null;

        String sql = "select p.nome_produto, pp.quantidade, pp.preco_unitario from produto_has_pedido pp join produto p on pp.COD_produto = p.ID_produto where COD_pedido = ?;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery();

            comando.setInt(1, pp);
            
            if (!resultado.next()) {
                return listaProdutoPedido;
            } else {
                do {
                    Produto_has_pedido pediprod = new Produto_has_pedido();
                    Produto produto = new Produto();
                    pediprod.setQuantidade(resultado.getInt("quantidade"));
                    pediprod.setPreco_unitario(resultado.getFloat("preco_unitario"));
                    produto.setNome_produto(resultado.getString("nome_produto"));
                    
                    pediprod.setProduto(produto);

                    listaProdutoPedido.add(pediprod);
                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdutoPedido;
    }
}
