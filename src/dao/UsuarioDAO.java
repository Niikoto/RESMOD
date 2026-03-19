package dao;

import factory.ConnectionFactory;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    Connection conectar = ConnectionFactory.getConnection();

    public void cadastrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario(ID_email, Nome, Senha, COD_cargo)" + " VALUES(?, ?, ?, ?)";

        // Cria-se uma variável temporária chamado "comando"
        // PreparedStamenet vai ser linkado com o conector do banco de dados
        // Então todos os comandos serão armazenados dentro desta variável
        // Para que o banco de dados possa ler ela.
        try(PreparedStatement comando = conectar.prepareStatement(sql)){
            // Aqui é o comando sento aplicado com setter
            // então você define o parâmetro certo
            // 1 = email, pois é o primeiro elemento
            // 2 = nome... e assim vai
            comando.setString(1, u.getId_Email());
            comando.setString(2, u.getNome());
            comando.setString(3, u.getSenha());
            comando.setInt   (4, u.getCargo());
            //Após definir o cadastro, ele vai executar
            comando.executeUpdate();
            //E fechar esta conexão
            conectar.close();
        }
    }
}
