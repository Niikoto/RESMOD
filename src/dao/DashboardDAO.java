package dao;

import factory.ConnectionFactory;
import java.sql.*;

public class DashboardDAO {

    public int contarUsuarios() throws Exception {

        String sql = "SELECT COUNT(*) FROM usuario";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        }

        return 0;
    }

    public int contarPorCargo(int cargo) throws Exception {

        String sql = "SELECT COUNT(*) FROM usuario WHERE COD_cargo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cargo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);
        }

        return 0;
    }
}