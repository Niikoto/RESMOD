package modelo;

public class Produto_has_pedido {
    private int ID_pedpro;
    private int COD_produto;
    private int COD_pedido;
    private int quantidade;
    private float preco_unitario;
    private Produto produto;
    
    //Constructor vazio
    public Produto_has_pedido() {}
    
    //Geters
    public int getID_pedpro() {return ID_pedpro;}

    public float getPreco_unitario() {return preco_unitario;}
    
    public int getQuantidade() {return quantidade;}
    
    public int getCOD_produto() {return COD_produto;}
    
    public int getCOD_pedido() {return COD_pedido;}

    public Produto geProduto(){return produto;}

    //Seters
    public void setCOD_produto(int COD_produto) {this.COD_produto = COD_produto;}

    public void setCOD_pedido(int COD_pedido) {this.COD_pedido = COD_pedido;}
    
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    
    public void setID_pedpro(int iD_pedpro) {this.ID_pedpro = iD_pedpro;}
    
    public void setPreco_unitario(float preco_unitario) {this.preco_unitario = preco_unitario;}

    public void setProduto(Produto produto){this.produto = produto;}
}
