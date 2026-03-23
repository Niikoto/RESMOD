package modelo;

public class Produto {
    private int ID_produto;
    private String nome_produto;
    private float preço;
    private int quantidade;
    private int minimo;
    private int COD_categoria;
    private int COD_fornecedor;

    //Constructor vazio
    public Produto(){}
    
    //geters
    public int getID_produto() {return ID_produto;}

    public String getNome_produto() {return nome_produto;}

    public float getPreço() {return preço;}

    public int getQuantidade() {return quantidade;}

    public int getMinimo() {return minimo;}

    public int getCOD_categoria() {return COD_categoria;}

    public int getCOD_fornecedor() {return COD_fornecedor;}

    public void setID_produto(int iD_produto) {ID_produto = iD_produto;}


    //Seters
    public void setNome_produto(String nome_produto) {this.nome_produto = nome_produto;}

    public void setPreço(float preço) {this.preço = preço;}

    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}

    public void setMinimo(int minimo) {this.minimo = minimo;}

    public void setCOD_categoria(int cOD_categoria) {COD_categoria = cOD_categoria;}

    public void setCOD_fornecedor(int cOD_fornecedor) {COD_fornecedor = cOD_fornecedor;}
}
