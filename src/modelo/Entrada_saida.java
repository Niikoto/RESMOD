package modelo;

import java.util.Date;

public class Entrada_saida {
    private int ID_entrada_saida;
    private boolean tipo;
    private int quantidade;
    private int COD_produto;
    private Date data;
    private String email;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Constructor vazio
    public Entrada_saida() {}

    //Geters
    public int getID_entrada_saida() {return ID_entrada_saida;}

    public int getCOD_produto() {return COD_produto;}

    public int getQuantidade() {return quantidade;}
    
    public boolean isTipo() {return tipo;}
    
    //Seters
    public void setTipo(boolean tipo) {this.tipo = tipo;}
    
    public void setID_entrada_saida(int iD_entrada_saida) {ID_entrada_saida = iD_entrada_saida;}
    
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    
    public void setCOD_produto(int cOD_produto) {COD_produto = cOD_produto;}
}
