package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Fornecedor;
import modelo.Produto;
import modelo.Produto_has_pedido;

public class Produto_has_pedidoDAO {
    Connection conectar = ConnectionFactory.getConnection();

    public List<Produto_has_pedido> listarProdutosPedidos(int pp) {
        List<Produto_has_pedido> listaProdutoPedido = new ArrayList<>();
        ResultSet resultado = null;

        String sql = "select p.ID_produto, p.nome_produto, p.preco, f.nome_fornecedor, pp.quantidade, pp.preco_unitario from produto_has_pedido pp join produto p on pp.COD_produto = p.ID_produto join fornecedor f on p.COD_CNPJ = f.CNPJ where pp.COD_pedido = ?;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setInt(1, pp);

            resultado = comando.executeQuery();

            if (!resultado.next()) {
                return listaProdutoPedido;
            } else {
                do {
                    Produto_has_pedido pediprod = new Produto_has_pedido();
                    Produto produto = new Produto();
                    Fornecedor fornecedor = new Fornecedor();

                    produto.setID_produto(resultado.getInt("ID_produto"));
                    produto.setNome_produto(resultado.getString("nome_produto"));
                    produto.setPreco(resultado.getFloat("preco"));
                    fornecedor.setNome_fornecedor(resultado.getString("nome_fornecedor"));
                    pediprod.setQuantidade(resultado.getInt("quantidade"));
                    pediprod.setPreco_unitario(resultado.getFloat("preco_unitario"));

                    pediprod.setProduto(produto);
                    pediprod.setFornecedor(fornecedor);

                    listaProdutoPedido.add(pediprod);
                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdutoPedido;
    }

    public void inserirProdPed(int numPed, Produto_has_pedido pp) throws SQLException {
        String sql = "insert into produto_has_pedido(COD_pedido, COD_produto, quantidade, preco_unitario) values (?, ?, ?, ?);";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setInt(1, numPed);
            comando.setInt(2, pp.getCOD_produto());
            comando.setInt(3, pp.getQuantidade());
            comando.setFloat(4, pp.getPreco_unitario());

            comando.executeUpdate();
        }
    }
}
