package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Compra;
import modelo.Usuario;

public class CompraDAO {
    Connection conexao = ConnectionFactory.getConnection();

    public void finalizarCompraPed(Compra c){
        String sql = "insert into compra(obs_compra, valor_da_compra, anexo_fiscal, data_compra, COD_pedido, COD_email) values(?,?,?,now(),?,?)";

        try(PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, c.getObs());
            comando.setFloat(2, c.getValor_da_compra());
            comando.setString(3, c.getAnexo_fiscal());
            comando.setInt(4, c.getCOD_pedido());
            comando.setString(5, c.getCOD_email());

            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Compra> listaCompras(){
        List<Compra> compras = new ArrayList<Compra>();
        
        String sql = "select c.ID_compra, c.obs_compra, c.valor_da_compra, c.anexo_fiscal, c.data_compra, c.COD_pedido, u.Nome from compra c inner join usuario u on c.COD_email = u.ID_email;";
        
        try(PreparedStatement comando = conexao.prepareStatement(sql)) {
            ResultSet res;
            res = comando.executeQuery();
            if (!res.next()) {
                return compras;
            }
            else{
                do{    
                    Compra compra = new Compra();
                    Usuario usuario = new Usuario();
    
                    compra.setID_compra(res.getInt(1));
                    compra.setObs_compra(res.getString(2));
                    compra.setValor_da_compra(res.getFloat(3));
                    compra.setAnexo_fiscal(res.getString(4));
                    compra.setData_compra(res.getString(5));
                    compra.setCOD_pedido(res.getInt(6));
                    usuario.setNome(res.getString(7));
                    compra.setUsuario(usuario);
    
                    compras.add(compra);
                }while (res.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compras;
    }
}