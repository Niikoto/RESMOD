package modelo;

import java.util.Date;

public class Historico_pedido {
    private int ID_historico;
    private String COD_email;
    private String status;
    private Date data_alteracao;
    private int COD_pedido;

    public Date getData() {
        return data_alteracao;
    }

    public void setData(Date data) {
        this.data_alteracao = data;
    }

    public String getEmail() {
        return COD_email;
    }

    public void setEmail(String email) {
        this.COD_email = email;
    }

    //Constructor vazio
    public Historico_pedido() {
    }

    public int getID_historico() {
        return ID_historico;
    }

    public void setID_historico(int ID_historico) {
        this.ID_historico = ID_historico;
    }

    public String getCOD_email() {
        return COD_email;
    }

    public void setCOD_email(String COD_email) {
        this.COD_email = COD_email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(Date data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public int getCOD_pedido() {
        return COD_pedido;
    }

    public void setCOD_pedido(int COD_pedido) {
        this.COD_pedido = COD_pedido;
    }
}



