package dao;

import model.usuario;
import util.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class usuarioDAO {

    public usuario buscarPorUsuario(String username) {
        String sql = "SELECT id, usuario, senha FROM usuarios WHERE usuario = ?";
        try (Connection con = DataBase.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("usuario");
                    String senha = rs.getString("senha");
                    return new usuario(id, user, senha);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}