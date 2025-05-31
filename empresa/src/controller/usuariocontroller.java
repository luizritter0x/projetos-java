package controller;

import dao.usuarioDAO;
import model.usuario;

public class usuariocontroller {
    private usuarioDAO dao = new usuarioDAO();

    public boolean autenticar(String usuario, String senha) {
        usuario user = dao.buscarPorUsuario(usuario);
        if(user != null) {
            return user.getSenha().equals(senha);
        }
        return false;
    }
}