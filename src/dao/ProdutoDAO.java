package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Categoria;
import modelo.Fornecedor;
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

    public List<Produto> listarProduto() {
        List<Produto> produtos = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        ResultSet resultado = null;
        String sql = "SELECT p.ID_produto, p.nome_produto,p.preco, p.quantidade, f.nome_fornecedor, c.categoria FROM produto p JOIN categoria c ON p.COD_categoria = c.ID_categoria JOIN fornecedor f ON p.COD_CNPJ = f.CNPJ;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery();

            if (!resultado.next()) {
                return produtos;
            } else {
                do {
                    Produto produto = new Produto();
                    Fornecedor fornecedor = new Fornecedor();
                    Categoria categoria = new Categoria();
                    produto.setID_produto(resultado.getInt(1));
                    produto.setNome_produto(resultado.getString(2));
                    produto.setPreco(resultado.getFloat(3));
                    produto.setQuantidade(resultado.getInt(4));
                    categoria.setNomeCategoria(resultado.getString(5));
                    fornecedor.setNome_fornecedor(resultado.getString(6));

                    produto.setCategoria(categoria);
                    produto.setFornecedor(fornecedor);
                    produtos.add(produto);

                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;

    }

    public List<Produto> listarProdutos(){
        List<Produto> produtos = new ArrayList<>();
        ResultSet resultado = null;
        String sql = "select p.ID_produto, p.nome_produto, p.preco, f.nome_fornecedor from produto p join fornecedor f on p.COD_CNPJ = f.CNPJ;";
        try(PreparedStatement comando = connetion.prepareStatement(sql)) {
            resultado = comando.executeQuery();
            if (!resultado.next()) {
                return produtos;
            }
            else{
                do{
                    Produto produto = new Produto();
                    Fornecedor fornecedor = new Fornecedor();

                    produto.setID_produto(resultado.getInt(1));
                    produto.setNome_produto(resultado.getString(2));
                    produto.setPreco(resultado.getFloat(3));
                    fornecedor.setNome_fornecedor(resultado.getString(4));

                    produto.setFornecedor(fornecedor);
                    produtos.add(produto);
                }while(resultado.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}