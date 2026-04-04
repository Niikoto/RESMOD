package dashboard;

import dao.DashboardDAO;

public class DashboardService {

    private DashboardDAO dao = new DashboardDAO();

    public DashboardData getDados() {

        DashboardData data = new DashboardData();

        try {

            // TOTAL DE USUÁRIOS
            data.setTotalUsuarios(dao.contarUsuarios());
            data.setTotalAdmins(dao.contarPorCargo(1));
            data.setTotalGerentes(dao.contarPorCargo(2));
            data.setTotalComuns(dao.contarPorCargo(3));
            // TOTAL NA TELA DE PEDIDOS
            data.setTotalAprovados(dao.contarPorStatus("aprovado"));
            data.setTotalNegados(dao.contarPorStatus("negado"));
            data.setTotalEmAberto(dao.contarPorStatus("em aberto"));
            data.setTotalEmAnalise(dao.contarPorStatus("em análise"));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}