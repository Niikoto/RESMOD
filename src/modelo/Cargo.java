package modelo;

public class Cargo {
    private int ID_cargo;
    private String tipo;
    private boolean adm;

    //Constructor vazio
    public Cargo(){}

    //geters
    public int getID_cargo() {return ID_cargo;}

    public String getTipo() {return tipo;}

    public boolean isAdm() {return adm;}

    //seters
    public void setID_cargo(int iD_cargo) {ID_cargo = iD_cargo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public void setAdm(boolean adm) {this.adm = adm;}
}
