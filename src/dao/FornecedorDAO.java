package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Fornecedor;

public class FornecedorDAO {
    Connection connection = ConnectionFactory.getConnection();

    public void cadastrarFornecedores(Fornecedor f) throws SQLException {
        String sql = "INSERT INTO fornecedor (`CNPJ`, nome_fornecedor, descricao, estado, telefone) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, f.getCNPJ());
            ps.setString(2, f.getNome_fornecedor());
            ps.setString(3, f.getDescricao());
            ps.setString(4, f.getEstado());
            ps.setString(5, f.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Fornecedor> listarFornecedores(){
        List<Fornecedor> fornecedores = new ArrayList<>();
        ResultSet resultado = null;

        String sql = "select CNPJ,nome_fornecedor from fornecedor;";

        try(PreparedStatement comando = connection.prepareStatement(sql)){
            resultado = comando.executeQuery();

            while (resultado.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setCNPJ(resultado.getString("CNPJ"));
                fornecedor.setNome_fornecedor(resultado.getString("nome_fornecedor"));

                fornecedores.add(fornecedor);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return fornecedores;
    }
}
