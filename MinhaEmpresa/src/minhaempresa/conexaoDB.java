package minhaempresa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaoDB {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public Connection conectar() {
        try{
            Connection conexaoBD = DriverManager.getConnection(URL,USUARIO,SENHA);
            System.out.println("Conexao bem sucedida");
            return conexaoBD;
        }catch(SQLException e) {
            System.out.println("Erro ao conectar ao DB");
            e.printStackTrace();
            return null;
        }
    }   
}