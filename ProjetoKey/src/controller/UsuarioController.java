package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean login(String usuario, String senha) {
        return usuarioDAO.autenticarUsuario(usuario, senha);
    }

    public boolean registrar(Usuario usuario) {
        return usuarioDAO.inserirUsuario(usuario);
    }

    public boolean isAdmin(String usuario, String senha) {
        return usuarioDAO.verificarAdmin(usuario, senha);
    }
}
