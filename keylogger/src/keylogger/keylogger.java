package keylogger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;

/**
 *
 * @author Luiz Ritter
 */

public class keylogger extends JFrame implements KeyListener {

    private boolean primeiraVez = true;

    public keylogger() {
        setTitle("keylogger educacional");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        salvarLog(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private void salvarLog(char c) {
        try (FileWriter fw = new FileWriter("logs.txt", true)) {
            if (primeiraVez) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                fw.write("\n\nLOGS DE: " + LocalDateTime.now().format(formatter) + "\n");
                primeiraVez = false;
            }

            if (c == KeyEvent.VK_SPACE || c == ' ') {
                fw.write(" ");
            } else if (c == '\n' || c == '\r') {
                fw.write(System.lineSeparator());
            } else {
                fw.write(c);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar log: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new keylogger();
    }
}
