package modelo;

public class Fornecedor {
    private int ID_fornecedor;
    private String nome_fornecedor;
    private String descricao;

    //Constructor vazio
    public Fornecedor() {}


    //Geters
    public int getID_fornecedor() {return ID_fornecedor;}

    public String getNome_fornecedor() {return nome_fornecedor;}

    public String getDescricao() {return descricao;}

    //Seters
    public void setID_fornecedor(int iD_fornecedor) {ID_fornecedor = iD_fornecedor;}

    public void setNome_fornecedor(String nome_fornecedor) {this.nome_fornecedor = nome_fornecedor;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    @Override
    public String toString(){
        return getNome_fornecedor();
    }
}
