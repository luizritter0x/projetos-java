package minhaempresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class ConsultarFuncionario {
    public void consultar() {
        conexaoDB conexaoBD = new conexaoDB();
        Connection conexao = null;

        try {
            conexao = conexaoBD.conectar();
            if (conexao != null) {
                String sql = "SELECT f.id, f.nome, f.cargo, fe.nome_atributo, fe.valor_atributo " +
                             "FROM funcionarios f " +
                             "LEFT JOIN funcionario_extras fe ON f.id = fe.funcionario_id " +
                             "ORDER BY f.id, fe.nome_atributo;";

                try (PreparedStatement ps = conexao.prepareStatement(sql);
                     ResultSet resultado = ps.executeQuery()) {

                    System.out.println("--- Lista de Funcionários ---");

                    Map<Integer, Map<String, Object>> funcionariosComAtributos = new LinkedHashMap<>();

                    while (resultado.next()) {
                        int id = resultado.getInt("id");
                        String nome = resultado.getString("nome");
                        String cargo = resultado.getString("cargo");
                        String nomeAtributo = resultado.getString("nome_atributo");
                        String valorAtributo = resultado.getString("valor_atributo");

                        funcionariosComAtributos.computeIfAbsent(id, k -> {
                            Map<String, Object> funcInfo = new LinkedHashMap<>();
                            funcInfo.put("nome", nome);
                            funcInfo.put("cargo", cargo);
                            funcInfo.put("atributos", new ArrayList<Map<String, String>>());
                            return funcInfo;
                        });

                        if (nomeAtributo != null && valorAtributo != null) {
                            List<Map<String, String>> atributos = (List<Map<String, String>>) funcionariosComAtributos.get(id).get("atributos");
                            Map<String, String> atributo = new LinkedHashMap<>();
                            atributo.put("nome", nomeAtributo);
                            atributo.put("valor", valorAtributo);
                            atributos.add(atributo);
                        }
                    }

                    if (funcionariosComAtributos.isEmpty()) {
                        System.out.println("Nenhum funcionário encontrado.");
                    } else {
                        for (Map.Entry<Integer, Map<String, Object>> entry : funcionariosComAtributos.entrySet()) {
                            int id = entry.getKey();
                            Map<String, Object> funcInfo = entry.getValue();
                            String nome = (String) funcInfo.get("nome");
                            String cargo = (String) funcInfo.get("cargo");
                            List<Map<String, String>> atributos = (List<Map<String, String>>) funcInfo.get("atributos");

                            System.out.println("\n------------------------------------");
                            System.out.println("ID: " + id);
                            System.out.println("Nome: " + nome);
                            System.out.println("Cargo: " + cargo);

                            if (!atributos.isEmpty()) {
                                System.out.println("Informações Adicionais:");
                                for (Map<String, String> atributo : atributos) {
                                    System.out.println("  - " + atributo.get("nome") + ": " + atributo.get("valor"));
                                }
                            } else {
                                System.out.println("  Nenhuma informação adicional.");
                            }
                            System.out.println("------------------------------------");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar funcionários e/ou atributos:");
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
