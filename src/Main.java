import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializar la pantalla de login
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
