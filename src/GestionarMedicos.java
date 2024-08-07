import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestionarMedicos extends JFrame {
    private JPanel panelmedicos;
    private JButton bmedico;
    private JButton cmedico;
    private JButton emedico;
    private JButton salir;
    private JButton amedico;

    public GestionarMedicos() {
        super("Gestionar Medicos");
        setContentPane(panelmedicos);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cmedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CrearDoctor().setVisible(true); // Mostrar la ventana CrearDoctor
            }
        });

        bmedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarDoctor().setVisible(true); // Mostrar la ventana BuscarDoctor
            }
        });

        amedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarDoctor().setVisible(true); // Mostrar la ventana ActualizarDoctor
            }
        });

        emedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EliminarDoctor().setVisible(true); // Mostrar la ventana EliminarDoctor
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Desea Salir del Sistema?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new Administrador().setVisible(true); // Mostrar la ventana Administrador
                }
            }
        });
    }

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionarMedicos().setVisible(true));
    }
}
