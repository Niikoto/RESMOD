package modelo;

public class Compra {
    private int ID_compra;
    private float valor_da_compra;
    private String anexo_fiscal;
    private int COD_pedido;
    
    //Constructor vazio
    public Compra() {}

    //Geters
    public int getID_compra() {return ID_compra;}
    
    public float getValor_da_compra() {return valor_da_compra;}
    
    public String getAnexo_fiscal() {return anexo_fiscal;}
    
    public int getCOD_pedido() {return COD_pedido;}

    //Seters
    public void setAnexo_fiscal(String anexo_fiscal) {this.anexo_fiscal = anexo_fiscal;}

    public void setValor_da_compra(float valor_da_compra) {this.valor_da_compra = valor_da_compra;}

    public void setID_compra(int iD_compra) {ID_compra = iD_compra;}

    public void setCOD_pedido(int cOD_pedido) {COD_pedido = cOD_pedido;}
}
