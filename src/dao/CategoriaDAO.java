package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Categoria;

public class CategoriaDAO {
    Connection connection = ConnectionFactory.getConnection();

    public List<Categoria> listarCategoria(){
        List<Categoria> categorias = new ArrayList<>();
        ResultSet resutado = null;

        String sql = "select ID_categoria, categoria from categoria";
        try(PreparedStatement comando = connection.prepareStatement(sql)){
            resutado = comando.executeQuery();

            while (resutado.next()) {
                Categoria categoria = new Categoria();
                categoria.setID_categoria(resutado.getInt(1));
                categoria.setNomeCatetegoria(resutado.getString(2));

                categorias.add(categoria);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return categorias;
    }
}
