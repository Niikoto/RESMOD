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
import modelo.Usuario;

public class PedidoDAO {
    Connection conectar = ConnectionFactory.getConnection();

    public int cadastrarPedido(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedido(criado, `status`, motivo, preco_total, forma_de_pagamento, segunda_forma_de_pagamento, COD_email, setor, centro_custo) VALUES(NOW(), ?, ?, 0, ?, ?, ?, ?, ?);";

        int idPed = 0;

        try (PreparedStatement comando = conectar.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, p.getStatus());
            comando.setString(2, p.getMotivo());
            comando.setString(3, p.getForma_de_pagamento());
            comando.setString(4, p.getSegunda_forma_de_pagamento());
            comando.setString(5, p.getCOD_email());
            comando.setString(6, p.getSetor());
            comando.setString(7, p.getCentro_custo());

            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                return idPed = resultado.getInt(1);
            }

            return idPed;
        }
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        ResultSet resultado = null;
        String sql = "SELECT p.ID_pedido, p.motivo,p.forma_de_pagamento, DATE_FORMAT(p.criado, '%d/%m/%Y') as 'criado', p.status, p.preco_total, p.COD_email, u.nome, p.setor, p.centro_custo FROM pedido p JOIN usuario u ON p.COD_email = u.ID_email order by p.ID_pedido desc;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery();

            if (!resultado.next()) {
                return pedidos;
            } else {
                do {
                    Pedido pedido = new Pedido();
                    Usuario usuario = new Usuario();
                    pedido.setID_pedido(resultado.getInt("ID_pedido"));
                    pedido.setMotivo(resultado.getString("motivo"));
                    pedido.setForma_de_pagamento(resultado.getString("forma_de_pagamento"));
                    pedido.setCriado(resultado.getString("criado"));
                    pedido.setStatus(resultado.getString("status"));
                    pedido.setPreco_total(resultado.getFloat("preco_total"));
                    pedido.setCOD_email(resultado.getString("COD_email"));
                    pedido.setSetor(resultado.getString("setor"));
                    pedido.setCentro_custo(resultado.getString("centro_custo"));
                    usuario.setNome(resultado.getString("nome"));

                    pedido.setUsuario(usuario);
                    pedidos.add(pedido);
                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;

    }

    public List<Pedido> listarPedidosFiltrados(String setor, String centroCusto) {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();

        StringBuilder sql = new StringBuilder(
                "SELECT p.ID_pedido, p.motivo, p.forma_de_pagamento, " +
                        "DATE_FORMAT(p.criado, '%d/%m/%Y') AS criado, p.status, p.preco_total, " +
                        "p.COD_email, u.nome, p.setor, p.centro_custo " +
                        "FROM pedido p JOIN usuario u ON p.COD_email = u.ID_email WHERE 1=1 ");

        if (setor != null && !setor.isEmpty())
            sql.append("AND p.setor = ? ");
        if (centroCusto != null && !centroCusto.isEmpty())
            sql.append("AND p.centro_custo = ? ");
        sql.append("ORDER BY p.ID_pedido DESC;");

        try (PreparedStatement comando = conectar.prepareStatement(sql.toString())) {
            int idx = 1;
            if (setor != null && !setor.isEmpty())
                comando.setString(idx++, setor);
            if (centroCusto != null && !centroCusto.isEmpty())
                comando.setString(idx++, centroCusto);

            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Pedido pedido   = new Pedido();
                Usuario usuario = new Usuario();

                pedido.setID_pedido(resultado.getInt("ID_pedido"));
                pedido.setMotivo(resultado.getString("motivo"));
                pedido.setForma_de_pagamento(resultado.getString("forma_de_pagamento"));
                pedido.setCriado(resultado.getString("criado"));
                pedido.setStatus(resultado.getString("status"));
                pedido.setPreco_total(resultado.getFloat("preco_total"));
                pedido.setCOD_email(resultado.getString("COD_email"));
                pedido.setSetor(resultado.getString("setor"));
                pedido.setCentro_custo(resultado.getString("centro_custo"));
                usuario.setNome(resultado.getString("nome"));
                pedido.setUsuario(usuario);

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    // Total gasto por centro de custo
    public List<DashboardData> totalPorCentroCusto() {
        List<DashboardData> lista = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        String sql = "SELECT centro_custo, SUM(preco_total) AS total FROM pedido WHERE centro_custo IS NOT NULL AND centro_custo != '' GROUP BY centro_custo ORDER BY total DESC;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                DashboardData d = new DashboardData();
                d.setNome_cargo(resultado.getString("centro_custo"));
                d.setQuantidade_pedido(resultado.getInt("total"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // total gasto por setor
    public List<DashboardData> totalPorSetor() {
        List<DashboardData> lista = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        String sql = "SELECT setor, SUM(preco_total) AS total FROM pedido WHERE setor IS NOT NULL AND setor != '' GROUP BY setor ORDER BY total DESC;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                DashboardData d = new DashboardData();
                d.setNome_cargo(resultado.getString("setor"));
                d.setQuantidade_pedido(resultado.getInt("total"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
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

    public void upDatePrecoTotal(int id, float valorTotal){
        String sql = "update pedido set preco_total = ? where ID_pedido = ?;";

        try(PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setFloat(1, valorTotal);
            comando.setInt(2, id);
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int idPedido, String novoStatus, String novoMotivo) {
        String sql = "UPDATE pedido SET status = ?, motivo = ? WHERE ID_pedido = ?;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setString(1, novoStatus);
            comando.setString(2, novoMotivo);
            comando.setInt(3, idPedido);
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean buttonExcluirpedido(int idPedido) {
        //  primeiro apagar os filhos que referenciam pedido em si
        // depois apagar o pedido em si.
        String[] sqls = {
            "DELETE FROM compra              WHERE COD_pedido = ?;",
            "DELETE FROM historico_pedido    WHERE COD_pedido = ?;",
            "DELETE FROM produto_has_pedido  WHERE COD_pedido = ?;",
            "DELETE FROM pedido              WHERE ID_pedido  = ?;"
        };
        try {
            for (String sql : sqls) {
                try (PreparedStatement cmd = conectar.prepareStatement(sql)) {
                    cmd.setInt(1, idPedido);
                    cmd.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void upDataStatus(String status, int nPed){
        String sql = "update pedido set status = ? where ID_pedido = ?;";
        try(PreparedStatement comando = conectar.prepareStatement(sql)) {
            comando.setString(1,status);
            comando.setInt(2,nPed);

            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> listarPedidosNaoNegado() {
        List<Pedido> pedidos = new ArrayList<>();
        Connection conectar = ConnectionFactory.getConnection();
        ResultSet resultado = null;
        String sql = "SELECT p.ID_pedido, p.motivo,p.forma_de_pagamento, DATE_FORMAT(p.criado, '%d/%m/%Y') as 'criado', p.status, p.preco_total, p.COD_email, u.nome, p.setor, p.centro_custo FROM pedido p JOIN usuario u ON p.COD_email = u.ID_email where p.status <> 'negado' and p.status <> 'finalizado' order by p.ID_pedido desc;";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery();

            if (!resultado.next()) {
                return pedidos;
            } else {
                do {
                    Pedido pedido = new Pedido();
                    Usuario usuario = new Usuario();
                    pedido.setID_pedido(resultado.getInt("ID_pedido"));
                    pedido.setMotivo(resultado.getString("motivo"));
                    pedido.setForma_de_pagamento(resultado.getString("forma_de_pagamento"));
                    pedido.setCriado(resultado.getString("criado"));
                    pedido.setStatus(resultado.getString("status"));
                    pedido.setPreco_total(resultado.getFloat("preco_total"));
                    pedido.setCOD_email(resultado.getString("COD_email"));
                    pedido.setSetor(resultado.getString("setor"));
                    pedido.setCentro_custo(resultado.getString("centro_custo"));
                    usuario.setNome(resultado.getString("nome"));

                    pedido.setUsuario(usuario);
                    pedidos.add(pedido);
                } while (resultado.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;

    }
}