package dao;

import factory.ConnectionFactory;
import modelo.Atualizacao;
import modelo.Cargo;
import modelo.DashboardData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    public int contarUsuarios() throws Exception {
        String sql = "SELECT COUNT(*) FROM usuario";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int contarPorCargo(int cargo) throws Exception {
        String sql = "SELECT COUNT(*) FROM usuario WHERE COD_cargo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cargo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // MeTODO PARA CONTAR POR STATUS NA TELA DE PEDIDOS
    public int contarPorStatus(String status) throws Exception {
        //SELECIONA TODOS
        String sql = "SELECT COUNT(*) FROM pedido WHERE status = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // aq é o novo metodo pra add as atualizaçao na tela, que tava no desing do figma

    public void inserirAtualizacao(String titulo, String mensagem) throws Exception {
        String sql = "INSERT INTO atualizacao (titulo, mensagem) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, mensagem);
            stmt.executeUpdate();
        }
    }

    public List<Atualizacao> listarAtualizacoes(String emailUsuarioLogado) throws Exception {
        List<Atualizacao> lista = new ArrayList<>();
        String sql = "SELECT a.ID_atualizacao, a.titulo, a.mensagem, DATE_FORMAT(a.data_hora, '%d/%m %H:%i') as data, " +
                "(SELECT COUNT(*) FROM atualizacao_lida al WHERE al.ID_atualizacao = a.ID_atualizacao AND al.ID_email = ?) as foi_lida " +
                "FROM atualizacao a ORDER BY a.data_hora DESC LIMIT 10";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emailUsuarioLogado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atualizacao a = new Atualizacao();
                a.setId(rs.getInt("ID_atualizacao"));
                a.setTitulo(rs.getString("titulo"));
                a.setMensagem(rs.getString("mensagem"));
                a.setDataHora(rs.getString("data"));
                a.setLida(rs.getInt("foi_lida") > 0);
                lista.add(a);
            }
        }
        return lista;
    }

    public List<DashboardData> contarPedidosPorCargo() throws Exception {
        List<DashboardData> lista = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();
        String sql = "SELECT c.tipo, COUNT(u.COD_cargo) FROM pedido p JOIN usuario u ON p.COD_email = u.ID_email JOIN cargo c ON u.COD_cargo = c.ID_cargo GROUP BY c.tipo ORDER BY COUNT(u.COD_cargo) DESC LIMIT 5;";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                return lista;
            } else {
                do{
                    DashboardData info_temp = new DashboardData();
                    info_temp.setNome_cargo(rs.getString(1));
                    info_temp.setQuantidade_pedido(rs.getInt(2));
                    lista.add(info_temp);
                } while (rs.next());
            }
        } return lista;
    }

    public void marcarComoLida(int idAtualizacao, String emailUsuario) throws Exception {
        String sql = "INSERT IGNORE INTO atualizacao_lida (ID_atualizacao, ID_email) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAtualizacao);
            stmt.setString(2, emailUsuario);
            stmt.executeUpdate();
        }
    }

    // coiso para deletar atualizaçao
    public void deletarAtualizacao(int idAtualizacao) throws Exception {
        String sql1 = "DELETE FROM atualizacao_lida WHERE ID_atualizacao = ?";
        String sql2 = "DELETE FROM atualizacao WHERE ID_atualizacao = ?";
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Limpa as leituras primeiro p nao dar BO com o banco
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, idAtualizacao);
                stmt1.executeUpdate();
            }
            // Deleta a atualização em si
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt2.setInt(1, idAtualizacao);
                stmt2.executeUpdate();
            }
        }
    }
}