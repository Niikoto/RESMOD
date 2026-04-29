package modelo;

public class Categoria {
    private int ID_categoria;
    private String nomeCategoria;

    //Constructor vazio
    public Categoria(){}

    //geters
    public int getID_categoria() {return ID_categoria;}

    public String getNomeCategoria() {return nomeCategoria;}

    //Seters
    public void setNomeCategoria(String nomeCatetegoria) {this.nomeCategoria = nomeCatetegoria;}

    public void setID_categoria(int iD_categoria) {ID_categoria = iD_categoria;}

    @Override
    public String toString(){
        return getNomeCategoria();
    }
}
