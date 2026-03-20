package dao;

import factory.ConnectionFactory;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    Connection conectar = ConnectionFactory.getConnection();

    public void cadastrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario(ID_email, Nome, Senha, COD_cargo)" + " VALUES(?, ?, ?, ?)";


        try(PreparedStatement comando = conectar.prepareStatement(sql)){


            // 1 = email, pois é o primeiro elemento
            // 2 = nome... e assim va indo
            comando.setString(1, u.getId_Email());
            comando.setString(2, u.getNome());
            comando.setString(3, u.getSenha());
            comando.setInt   (4, u.getCargo());
            //dps de definir o catastro ele vai
            comando.executeUpdate();
            //E fechar esta conexão
            conectar.close();
        }
    }

    public Usuario verificar(Usuario u) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE ID_email = ? AND Senha = ?";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setString(1, u.getId_Email());
            comando.setString(2, u.getSenha());

            ResultSet resultado = comando.executeQuery();

            // Se houver um próximo registro, significa que o login é válido
            if (resultado.next()) {
                // Puxa o número do cargo lá do banco de dados(o treco de 1, 2 3)e salva no nosso objeto
                u.setCargo(resultado.getInt("COD_cargo"));
                return u; 
            }

            return null; // Se não achou ninguém ele devolve vazio
        }
    }
}