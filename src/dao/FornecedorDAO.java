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
        String sql = "INSERT INTO fornecedor (CNPJ, nome_fornecedor, descricao, estado, municipio, rua, numero, telefone) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, f.getCNPJ());
            ps.setString(2, f.getNome_fornecedor());
            ps.setString(3, f.getDescricao());
            ps.setString(4, f.getEstado());
            ps.setString(5, f.getMunicipio());
            ps.setString(6, f.getRua());
            ps.setInt(7, f.getNumero());
            ps.setString(8, f.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Fornecedor> listarFornecedores(Fornecedor f) {
        List<Fornecedor> fornecedores = new ArrayList<>();
        ResultSet resultado = null;

        String sql = "select CNPJ, nome_fornecedor, descricao, estado, municipio, rua, numero, telefone from fornecedor where nome_fornecedor like ? and CNPJ like ? and estado like ?;";

        try (PreparedStatement comando = connection.prepareStatement(sql)) {
            comando.setString(1, "%" + f.getNome_fornecedor() + "%");
            comando.setString(2, "%" + f.getCNPJ() + "%");
            comando.setString(3, "%" + f.getEstado() + "%");

            resultado = comando.executeQuery();

            while (resultado.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setCNPJ(resultado.getString("CNPJ"));
                fornecedor.setNome_fornecedor(resultado.getString("nome_fornecedor"));
                fornecedor.setDescricao(resultado.getString("descricao"));
                fornecedor.setEstado(resultado.getString("estado"));
                fornecedor.setMunicipio(resultado.getString("municipio"));
                fornecedor.setRua(resultado.getString("rua"));
                fornecedor.setNumero(resultado.getInt("numero"));
                fornecedor.setTelefone(resultado.getString("telefone"));

                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fornecedores;
    }

    public List<Fornecedor> listarFornecedor() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        ResultSet resultado = null;

        String sql = "select CNPJ, nome_fornecedor from fornecedor;";

        try (PreparedStatement comando = connection.prepareStatement(sql)) {
            resultado = comando.executeQuery();

            if (!resultado.next()) {
                return fornecedores;
            }else{
                do{
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setCNPJ(resultado.getString("CNPJ"));
                    fornecedor.setNome_fornecedor(resultado.getString("nome_fornecedor"));
    
                    fornecedores.add(fornecedor);
                }while (resultado.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fornecedores;
    }
}
