package controller;

import dao.KeyDAO;
import model.KeyRegistro;
import java.util.List;

public class KeyController {
    private KeyDAO keyDAO = new KeyDAO();

    public boolean validarChave(String chave) {
        return keyDAO.validarKey(chave);
    }

    public void usarChave(String chave) {
        keyDAO.marcarKeyComoUsada(chave);
    }

    public boolean inserirKey(String chave) {
        return keyDAO.inserirKey(chave);
    }

    public List<KeyRegistro> listarKeys() {
        return keyDAO.listarKeys();
    }
}
