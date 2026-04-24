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

    public List<Fornecedor> listarFornecedores(){
        List<Fornecedor> fornecedores = new ArrayList<>();
        ResultSet resultado = null;

        String sql = "select ID_fornecedor,nome_fornecedor from fornecedor;";

        try(PreparedStatement comando = connection.prepareStatement(sql)){
            resultado = comando.executeQuery();

            while (resultado.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setID_fornecedor(resultado.getInt(1));
                fornecedor.setNome_fornecedor(resultado.getString(2));

                fornecedores.add(fornecedor);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return fornecedores;
    }
}
