import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistorialMedico extends JFrame {
    private JButton viewMedicalHistoryButton;
    private JTextArea displayArea;

    public HistorialMedico() {
        // Configuración de la ventana principal
        setTitle("Historial Médico - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        viewMedicalHistoryButton = new JButton("Ver Historial Médico");

        buttonPanel.add(viewMedicalHistoryButton);

        // Área de visualización
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Agregar paneles al marco
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Acciones de los botones
        viewMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialMedico();
            }
        });
    }

    // Método para ver el historial médico
    private void verHistorialMedico() {
        JTextField pacienteIdField = new JTextField();

        Object[] message = {
                "ID del Paciente:", pacienteIdField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Ver Historial Médico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String pacienteId = pacienteIdField.getText().trim();

            if (!pacienteId.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement("SELECT * FROM HistorialMedico WHERE pacienteId = ?")) {
                    ps.setInt(1, Integer.parseInt(pacienteId));
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            sb.append("ID: ").append(rs.getInt("id")).append("\n");
                            sb.append("Paciente ID: ").append(rs.getInt("pacienteId")).append("\n");
                            sb.append("Fecha: ").append(rs.getString("fecha")).append("\n");
                            sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n\n");
                        }
                    }
                    displayArea.setText(sb.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    displayArea.setText("Error al obtener historial médico.");
                }
            } else {
                displayArea.setText("Por favor, complete todos los campos.");
            }
        }
    }

    // Método para obtener la conexión a la base de datos
    public Connection getConnection() throws Exception {
        String URL = "jdbc:mysql://localhost:3306/proyectofinal";
        String USER = "root";
        String PASSWORD = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> new HistorialMedico().setVisible(true));
    }
}
