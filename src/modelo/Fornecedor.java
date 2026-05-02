package modelo;

public class Fornecedor {
    private String CNPJ;
    private String nome_fornecedor;
    private String descricao;
    private String estado;
    private String municipio;
    private String telefone;
    private String rua;
    private int numero;

    //Constructor vazio
    public Fornecedor() {}

    //Geters
    public String getCNPJ() {return CNPJ;}
    public String getNome_fornecedor() {return nome_fornecedor;}
    public String getDescricao() {return descricao;}
    public String getEstado() {return estado;}
    public String getMunicipio() {return municipio;}
    public String getTelefone() {return telefone;}
    public int getNumero() {return numero;}
    public String getRua() {return rua;}

    //Seters
    public void setCNPJ(String CNPJ) {this.CNPJ = CNPJ;}
    public void setNome_fornecedor(String nome_fornecedor) {this.nome_fornecedor = nome_fornecedor;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setMunicipio(String municipio) {this.municipio = municipio;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public void setRua(String rua) {this.rua = rua;}
    public void setNumero(int numero) {this.numero = numero;}


    @Override
    public String toString(){
        return getNome_fornecedor();
    }
}
