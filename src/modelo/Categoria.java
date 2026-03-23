package modelo;

public class Categoria {
    private int ID_categoria;
    private String catetegoria;

    //Constructor vazio
    public Categoria(){}

    //geters
    public int getID_categoria() {return ID_categoria;}

    public String getCatetegoria() {return catetegoria;}

    //Seters
    public void setCatetegoria(String catetegoria) {this.catetegoria = catetegoria;}

    public void setID_categoria(int iD_categoria) {ID_categoria = iD_categoria;}
}
