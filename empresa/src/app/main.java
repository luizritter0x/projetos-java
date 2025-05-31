package app;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;
import view.loginframe;

public class main {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            loginframe login = new loginframe();
            login.setVisible(true);
        });
    }
}