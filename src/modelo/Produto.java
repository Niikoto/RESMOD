package modelo;

public class Produto {
    private int ID_produto;
    private String nome_produto;
    private float preco;
    private int quantidade;
    private int minimo;
    private int COD_categoria;
    private String COD_CNPJ;
    private Categoria categoria;
    private Fornecedor fornecedor;

    //Constructor vazio
    public Produto(){}
    
    //geters
    public int getID_produto() {return ID_produto;}

    public String getNome_produto() {return nome_produto;}

    public float getPreco() {return preco;}

    public int getQuantidade() {return quantidade;}

    public int getMinimo() {return minimo;}

    public int getCOD_categoria() {return COD_categoria;}

    public String getCOD_CNPJ() {return COD_CNPJ;}

    public void setID_produto(int iD_produto) {ID_produto = iD_produto;}

    public Categoria getCategoria() {return categoria;}

    public Fornecedor getFornecedor() {return fornecedor;}


    //Seters
    public void setNome_produto(String nome_produto) {this.nome_produto = nome_produto;}

    public void setPreco(float preco) {this.preco = preco;}

    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}

    public void setMinimo(int minimo) {this.minimo = minimo;}

    public void setCOD_categoria(int cOD_categoria) {this.COD_categoria = cOD_categoria;}

    public void setCOD_CNPJ(String COD_CNPJ) {this.COD_CNPJ = COD_CNPJ;}

    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    public void setFornecedor(Fornecedor fornecedor) {this.fornecedor = fornecedor;}
}
