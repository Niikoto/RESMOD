package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Cargo;

public class CargoDAO {
    public List<Cargo> listarCargo() { // Criar metodo para que guarda uma lista
        List<Cargo> cargos = new ArrayList<>(); // Objeto que guardar a lista em si

        Connection conectar = ConnectionFactory.getConnection();

        ResultSet resultado = null; // Vai guardar o resultado do select

        String sql = "select ID_cargo, tipo from Cargo;"; // Select da tabela Cargo
        try (PreparedStatement comando = conectar.prepareStatement(sql)) {
            resultado = comando.executeQuery(); // Execução do select

            while (resultado.next()) { // Isso vai guardar o resultados do select em dois campos
                Cargo cargo = new Cargo();
                cargo.setID_cargo(resultado.getInt(1)); // Campo 1 o ID
                cargo.setTipo(resultado.getString(2)); // Campo 2 o nome do cargo

                cargos.add(cargo); // Vai adicionar os campos a lista criada
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Se der erro
        }
        return cargos; // return da lista criada
    }

    public Cargo consultarCargo(Cargo car) throws SQLException {//select para guardar o cargo do usario e verificar se ele tem acesso admistrador
        Connection conectar = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM cargo WHERE ID_cargo = ?";

        try (PreparedStatement comando = conectar.prepareStatement(sql)) {

            comando.setInt(1, car.getID_cargo());
            ResultSet rs = comando.executeQuery();//guardar o retorno do sql
            if (rs.next()) {//caso tenha retorno
                car.setID_cargo(rs.getInt("ID_cargo"));//guarda no objeto enviado para ca
                car.setTipo(rs.getString("tipo"));
                car.setAdm(rs.getBoolean("adm"));
            }
        }

        return car;//retorna o objeto com informações cadastradas
    }

    public void cadastrarCargo(Cargo c)throws SQLException{
        Connection conexao = ConnectionFactory.getConnection();
        String sql = "insert into cargo(tipo,adm) values(?, ?);";

        try(PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, c.getTipo());
            comando.setBoolean(2, c.isAdm());

            comando.executeUpdate();
        }
    }
}