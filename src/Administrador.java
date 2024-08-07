import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrador extends JFrame {
    private JPanel paneladmin;
    private JLabel menuadmin;
    private JButton gestionarMedicosButton;
    private JButton reportesButton;
    private JButton salir;
    private JButton Gpacientes;

    public Administrador() {
        super("Menu Administrador");
        setContentPane(paneladmin);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        gestionarMedicosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestionarMedicos().setVisible(true); // Asegúrate de mostrar la ventana
            }
        });

        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Reportes().setVisible(true); // Asegúrate de mostrar la ventana
            }
        });

        Gpacientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionarPacientes().setVisible(true); // Asegúrate de mostrar la ventana
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Desea Salir del Sistema?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login().setVisible(true); // Asegúrate de mostrar la ventana
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Administrador().setVisible(true));
    }
}
