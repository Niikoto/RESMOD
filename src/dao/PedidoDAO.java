package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.DashboardData;
import modelo.Pedido;
import modelo.Session;
import modelo.Usuario;

public class PedidoDAO {
    Connection conectar = ConnectionFactory.getConnection();

    public void cadastrarPedido(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedido(criado, `status`, motivo, preco_total, forma_de_pagamento, segunda_forma_de_pagamento, COD_email) VALUES(NOW(), ?, ?, 0, ?, ?, ?);";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setString(1, p.getStatus());
            comando.setString(2, p.getMotivo());
            comando.setString(3, p.getForma_de_pagamento());
            comando.setString(4, p.getSegunda_forma_de_pagamento());
            comando.setString(5, p.getCOD_email());

            comando.executeUpdate();
            conectar.close();
        }
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        ResultSet resultado = null;
        String sql = "SELECT p.ID_pedido, p.motivo,p.forma_de_pagamento, DATE_FORMAT(p.criado, '%d/%m/%Y') as 'criado', p.status, p.preco_total, p.COD_email, u.nome FROM pedido p JOIN usuario u ON p.COD_email = u.ID_email;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery();

            if (!resultado.next()) {
                return pedidos;
            } else {
                do {
                    Pedido pedido = new Pedido();
                    Usuario usuario = new Usuario();
                    pedido.setID_pedido(resultado.getInt(1));
                    pedido.setMotivo(resultado.getString(2));
                    pedido.setForma_de_pagamento(resultado.getString(3));
                    pedido.setCriado(resultado.getString(4));
                    pedido.setStatus(resultado.getString(5));
                    pedido.setPreco_total(resultado.getFloat(6));
                    pedido.setCOD_email(resultado.getString(7));
                    usuario.setNome(resultado.getString(8));

                    pedido.setUsuario(usuario);
                    pedidos.add(pedido);
                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;

    }

    public DashboardData contarPedidos() {
        String sql = "select count(case when status = 'em aberto' then 1 end) as emAberto, count(case when status = 'em analise' then 1 end) as emAnalise, count(case when status = 'aprovado' then 1 end) as aprovado, count(case when status = 'negado' then 1 end) as negado from pedido;";

        ResultSet resultado = null;

        DashboardData dashData = new DashboardData();

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery();


            if (resultado.next()) {
                dashData.setTotalEmAberto(resultado.getInt("emAberto"));
                dashData.setTotalEmAnalise(resultado.getInt("emAnalise"));
                dashData.setTotalAprovados(resultado.getInt("aprovado"));
                dashData.setTotalNegados(resultado.getInt("negado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dashData;

    }

    public int trazerCodPed(){
        int numPed = 0;
        String sql = "select ID_pedido from pedido where COD_email = ? order by ID_pedido desc limit 1;";

        try(PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setString(1, Session.getUsuario().getId_Email());
            numPed = comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numPed;
    }

}