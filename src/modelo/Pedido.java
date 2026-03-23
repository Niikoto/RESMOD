package modelo;

public class Pedido {
    private int ID_pedido;
    private String criado;
    private String status;
    private String data_aprovacao;
    private float preco_total;
    private String forma_de_pagamento;
    private String motivo;
    private int COD_email;
    

    //Constructor vazio
    public Pedido() {}

    //Geters
    public int getID_pedido() {return ID_pedido;}

    public String getCriado() {return criado;}
    
    public String getStatus() {return status;}
    
    public String getData_aprovacao() {return data_aprovacao;}
    
    public float getPreco_total() {return preco_total;}
    
    public String getForma_de_pagamento() {return forma_de_pagamento;}
    
    public String getMotivo() {return motivo;}
    
    public int getCOD_email() {return COD_email;}
    
    //Seters
    public void setMotivo(String motivo) {this.motivo = motivo;}

    public void setForma_de_pagamento(String forma_de_pagamento) {this.forma_de_pagamento = forma_de_pagamento;}

    public void setPreco_total(float preco_total) {this.preco_total = preco_total;}

    public void setData_aprovacao(String data_aprovacao) {this.data_aprovacao = data_aprovacao;}

    public void setStatus(String status) {this.status = status;}

    public void setCriado(String criado) {this.criado = criado;}

    public void setID_pedido(int iD_pedido) {ID_pedido = iD_pedido;}

    public void setCOD_email(int cOD_email) {COD_email = cOD_email;}
}
