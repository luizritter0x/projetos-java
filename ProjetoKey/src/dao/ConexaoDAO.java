/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoDAO {
    public Connection conectaBD() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/dbluiz";
            String user = "root";
            String password = "root"; 
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Erro na conexão: " + e);
        }
        return conn;
    }
}