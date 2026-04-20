package dao;

import factory.ConnectionFactory;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    Connection conectar = ConnectionFactory.getConnection();

    public void cadastrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario(ID_email, Nome, Senha, COD_cargo) VALUES(?, ?, ?, ?)";

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
                // setta também o nome do usuário caso ele tenha um.
                u.setNome(resultado.getString("Nome"));
                return u;
            }

            return null; // Se não achou ninguém ele devolve vazio
        }
    }

    // o cod pra busca de funcionario,
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        // aq é so pra ele ver o back pra trazer os funcionario
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement comando = conn.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Usuario u = new Usuario();

                // Puxa os dados das colunas do banco e salv
                u.setId_email(resultado.getString("ID_email"));
                u.setNome(resultado.getString("Nome"));
                u.setCargo(resultado.getInt("COD_cargo"));

                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar os funcionários: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}