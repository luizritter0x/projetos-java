/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.KeyController;
import controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import model.Usuario;


public class JPainel extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JPainel.class.getName());
    private UsuarioController usuarioController = new UsuarioController();
    private KeyController keyController = new KeyController();

    private JTextField tfUsuarioLogin;
    private JPasswordField pfSenhaLogin;
    private JButton btnLogin;

    private JTextField tfNomeRegistro;
    private JTextField tfUsuarioRegistro;
    private JPasswordField pfSenhaRegistro;
    private JTextField tfChaveRegistro;
    private JButton btnRegistrar;
   
    public JPainel() {
        initComponents();

        setTitle("Login e Registro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane abas = new JTabbedPane();

        JPanel painelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfUsuarioLogin = new JTextField(20);
        pfSenhaLogin = new JPasswordField(20);
        btnLogin = new JButton("Login");

        c.gridx = 0; c.gridy = 0;
        painelLogin.add(new JLabel("Usuário:"), c);
        c.gridx = 1; c.gridy = 0;
        painelLogin.add(tfUsuarioLogin, c);

        c.gridx = 0; c.gridy = 1;
        painelLogin.add(new JLabel("Senha:"), c);
        c.gridx = 1; c.gridy = 1;
        painelLogin.add(pfSenhaLogin, c);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        painelLogin.add(btnLogin, c);

        btnLogin.addActionListener(e -> fazerLogin());

        JPanel painelRegistro = new JPanel(new GridBagLayout());

        tfNomeRegistro = new JTextField();
        tfNomeRegistro.setPreferredSize(new Dimension(300, 35));
    
        tfUsuarioRegistro = new JTextField();
        tfUsuarioRegistro.setPreferredSize(new Dimension(250, 28));

        pfSenhaRegistro = new JPasswordField();
        pfSenhaRegistro.setPreferredSize(new Dimension(250, 28));

        tfChaveRegistro = new JTextField();
        tfChaveRegistro.setPreferredSize(new Dimension(250, 28));
        btnRegistrar = new JButton("Registrar");

        c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
painelRegistro.add(new JLabel("Nome:"), c);
c.gridx = 1; c.gridy = 0;
c.weightx = 1;
painelRegistro.add(tfNomeRegistro, c);

c.gridx = 0; c.gridy = 1;
c.weightx = 0;
painelRegistro.add(new JLabel("Usuário:"), c);
c.gridx = 1; c.gridy = 1;
c.weightx = 1;
painelRegistro.add(tfUsuarioRegistro, c);

c.gridx = 0; c.gridy = 2;
c.weightx = 0;
painelRegistro.add(new JLabel("Senha:"), c);
c.gridx = 1; c.gridy = 2;
c.weightx = 1;
painelRegistro.add(pfSenhaRegistro, c);

c.gridx = 0; c.gridy = 3;
c.weightx = 0;
painelRegistro.add(new JLabel("Key:"), c);
c.gridx = 1; c.gridy = 3;
c.weightx = 1;
painelRegistro.add(tfChaveRegistro, c);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        painelRegistro.add(btnRegistrar, c);

        btnRegistrar.addActionListener(e -> fazerRegistro());

        abas.add("Login", painelLogin);
        abas.add("Registro", painelRegistro);

        add(abas, BorderLayout.CENTER);
    }

    private void fazerLogin() {
        String usuario = tfUsuarioLogin.getText().trim();
        String senha = new String(pfSenhaLogin.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        boolean loginOk = usuarioController.login(usuario, senha);
        if (loginOk) {
            boolean isAdmin = usuarioController.isAdmin(usuario, senha);
            if (isAdmin) {
                JOptionPane.showMessageDialog(this, "Login Admin realizado!");
                new JPainelAdmin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login usuário realizado!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!");
        }
    }

    private void fazerRegistro() {
        String nome = tfNomeRegistro.getText().trim();
        String usuario = tfUsuarioRegistro.getText().trim();
        String senha = new String(pfSenhaRegistro.getPassword());
        String chave = tfChaveRegistro.getText().trim();

        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || chave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        boolean chaveValida = keyController.validarChave(chave);
        if (!chaveValida) {
            JOptionPane.showMessageDialog(this, "Chave inválida ou já usada!");
            return;
        }

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setUsuario(usuario);
        u.setSenha(senha);
        u.setAdmin(false); 

        boolean registrado = usuarioController.registrar(u);
        if (registrado) {
            keyController.usarChave(chave); 
            JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");
            tfNomeRegistro.setText("");
            tfUsuarioRegistro.setText("");
            pfSenhaRegistro.setText("");
            tfChaveRegistro.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar usuário!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JPainel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
