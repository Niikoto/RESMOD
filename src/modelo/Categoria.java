package modelo;

public class Categoria {
    private int ID_categoria;
    private String nomeCatetegoria;

    //Constructor vazio
    public Categoria(){}

    //geters
    public int getID_categoria() {return ID_categoria;}

    public String getNomeCatetegoria() {return nomeCatetegoria;}

    //Seters
    public void setNomeCatetegoria(String nomeCatetegoria) {this.nomeCatetegoria = nomeCatetegoria;}

    public void setID_categoria(int iD_categoria) {ID_categoria = iD_categoria;}

    @Override
    public String toString(){
        return getNomeCatetegoria();
    }
}
