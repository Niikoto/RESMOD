package dashboard;

public class DashboardData {

    // TELA DE PEDIDO
    private int totalAprovados;
    private int totalNegados;
    private int totalEmAberto;
    private int totalEmAnalise;

    // ?
    private int totalUsuarios;
    private int totalAdmins;
    private int totalGerentes;
    private int totalComuns;

    public int getTotalUsuarios() { return totalUsuarios; }
    public void setTotalUsuarios(int totalUsuarios) { this.totalUsuarios = totalUsuarios; }

    public int getTotalAdmins() { return totalAdmins; }
    public void setTotalAdmins(int totalAdmins) { this.totalAdmins = totalAdmins; }

    public int getTotalGerentes() { return totalGerentes; }
    public void setTotalGerentes(int totalGerentes) { this.totalGerentes = totalGerentes; }

    public int getTotalComuns() { return totalComuns; }
    public void setTotalComuns(int totalComuns) { this.totalComuns = totalComuns; }

    // GETTERS & SETTERS DA TELA DE PEDIDOS
    public int getTotalAprovados() { return totalAprovados; }
    public void setTotalAprovados(int v) { this.totalAprovados = v; }

    public int getTotalNegados() { return totalNegados; }
    public void setTotalNegados(int v) { this.totalNegados = v; }

    public int getTotalEmAberto() { return totalEmAberto; }
    public void setTotalEmAberto(int v) { this.totalEmAberto = v; }

    public int getTotalEmAnalise() { return totalEmAnalise; }
    public void setTotalEmAnalise(int v) { this.totalEmAnalise = v; }
}