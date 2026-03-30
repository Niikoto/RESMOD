package dashboard;

import dao.DashboardDAO;

public class DashboardService {

    private DashboardDAO dao = new DashboardDAO();

    public DashboardData getDados() {

        DashboardData data = new DashboardData();

        try {
            data.setTotalUsuarios(dao.contarUsuarios());
            data.setTotalAdmins(dao.contarPorCargo(1));
            data.setTotalGerentes(dao.contarPorCargo(2));
            data.setTotalComuns(dao.contarPorCargo(3));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}