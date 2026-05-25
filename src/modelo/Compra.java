package modelo;

public class Compra {
    private int ID_compra;
    private String obs_compra;
    private float valor_da_compra;
    private String anexo_fiscal;
    private String data_compra;
    private int COD_pedido;
    private String COD_email;

    private Usuario usuario;
    private Pedido pedido;
    
    //Constructor vazio
    public Compra() {}

    //Geters
    public int getID_compra() {return ID_compra;}

    public String getObs(){return obs_compra;}
    
    public float getValor_da_compra() {return valor_da_compra;}
    
    public String getAnexo_fiscal() {return anexo_fiscal;}

    public String getData_compra(){return data_compra;}
    
    public int getCOD_pedido() {return COD_pedido;}

    public String getCOD_email(){return COD_email;}

    public Usuario getUsuario(){return usuario;}

    public Pedido getPedido(){return pedido;}

    //Seters
    public void setAnexo_fiscal(String anexo_fiscal) {this.anexo_fiscal = anexo_fiscal;}

    public void setValor_da_compra(float valor_da_compra) {this.valor_da_compra = valor_da_compra;}

    public void setID_compra(int iD_compra) {ID_compra = iD_compra;}

    public void setData_compra(String data_compra){this.data_compra = data_compra;}

    public void setCOD_pedido(int cOD_pedido) {this.COD_pedido = cOD_pedido;}

    public void setCOD_email(int COD_email) {this.COD_pedido = COD_email;}

    public void setUsuario(Usuario usuario){this.usuario = usuario;}

    public void setPedido(Pedido pedido){this.pedido = pedido;}
}
