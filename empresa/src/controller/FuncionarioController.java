package controller;

import dao.funcionarioDAO;
import model.funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioController {
    private funcionarioDAO dao = new funcionarioDAO();

    public void adicionarFuncionario(String nome, String cargo, String telefone) throws SQLException {
        funcionario f = new funcionario(nome, cargo, telefone);
        dao.adicionar(f);
    }

    public List<funcionario> listarFuncionarios() throws SQLException {
        return dao.listar();
    }
}