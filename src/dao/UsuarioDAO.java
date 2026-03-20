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

    //Metodo para verificar se o email que o usuario colocou no login batem com o que tem no banco
    public void verificar(Usuario u) throws SQLException{
        String sql = "Select ID_email, senha from usuario where ID_email = ? and senha = ?";//comando SQL que verifica se tem o usuario descrito na tabela usuario

        try(PreparedStatement comando = conectar.prepareStatement(sql)){
            comando.setString(1,u.getId_Email());
            comando.setString(2,u.getSenha());

            ResultSet resultado = comando.executeQuery();//o executeQuery vai guardar no resultado o se foi encontrado na procura
            
            if(resultado.next()) {
                System.out.println("Usurio encontrado");//aqui mostra se encontrou algo
            }
            else{
                System.out.println("Usuario não encontrado");//aqui se não encontrar
            }
            conectar.close();
        }
    }
}
