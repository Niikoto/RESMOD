package modelo;

import dao.DashboardDAO;
import dao.PedidoDAO;

public class DashboardService {

    private DashboardDAO dao = new DashboardDAO();

    public DashboardData getDados() {

        DashboardData data = new DashboardData();

        PedidoDAO pedDao = new PedidoDAO();

        try {

            
            // TOTAL DE USUÁRIOS
            data.setTotalUsuarios(dao.contarUsuarios());
            data.setTotalAdmins(dao.contarPorCargo(1));
            data.setTotalGerentes(dao.contarPorCargo(2));
            data.setTotalComuns(dao.contarPorCargo(3));
            // TOTAL NA TELA DE PEDIDOS
            DashboardData dataped = pedDao.contarPedidos();

            data.setTotalAprovados(dataped.getTotalAprovados());
            data.setTotalNegados(dataped.getTotalNegados());
            data.setTotalEmAberto(dataped.getTotalEmAberto());
            data.setTotalEmAnalise(dataped.getTotalEmAnalise());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}