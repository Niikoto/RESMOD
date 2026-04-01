package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Pedido;
import modelo.Usuario;

public class PedidoDAO {
    Connection conectar = ConnectionFactory.getConnection();

    public void cadastrarPedido(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedido(criado, `status`, motivo, preço_total, COD_email) VALUES(NOW(), ?, ?, 0, ?);";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setString(1, p.getStatus());
            comando.setString(2, p.getMotivo());
            comando.setString(3, p.getCOD_email());

            comando.executeUpdate();
            conectar.close();
        }
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        ResultSet resultado = null;
        String sql = "SELECT p.ID_pedido, p.motivo,p.forma_de_pagamento, p.criado, p.status, p.preço_total, p.COD_email, u.nome FROM pedido p JOIN usuario u ON p.COD_email = u.ID_email;";

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
                    System.out.println("passei aqui");
                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Aqui não tá funcionando");
        return pedidos;

    }

}
