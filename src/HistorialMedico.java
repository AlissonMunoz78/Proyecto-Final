import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistorialMedico extends JFrame {
    private JButton registerMedicalHistoryButton;
    private JButton viewMedicalHistoryButton;
    private JTextArea displayArea;

    public HistorialMedico() {
        // Configuración de la ventana principal
        setTitle("Gestión de Historiales Médicos - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        registerMedicalHistoryButton = new JButton("Registrar Historial Médico");
        viewMedicalHistoryButton = new JButton("Ver Historial Médico");

        buttonPanel.add(registerMedicalHistoryButton);
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
        registerMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarHistorialMedico();
            }
        });

        viewMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialMedico();
            }
        });
    }

    // Método para registrar un nuevo historial médico
    private void registrarHistorialMedico() {
        JTextField patientIdField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] fields = {
                "ID del Paciente:", patientIdField,
                "ID del Médico:", doctorIdField,
                "Descripción:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Registrar Historial Médico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int patientId = Integer.parseInt(patientIdField.getText());
            int doctorId = Integer.parseInt(doctorIdField.getText());
            String description = descriptionField.getText();

            try {
                Connection con = getConnection();
                String query = "INSERT INTO HistorialesMedicos (pacienteId, medicoId, descripcion, fecha) VALUES (?, ?, ?, NOW())";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, patientId);
                ps.setInt(2, doctorId);
                ps.setString(3, description);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Historial médico registrado con éxito.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar el historial médico.");
            }
        }
    }

    // Método para ver los historiales médicos
    private void verHistorialMedico() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM HistorialesMedicos";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Historial: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los historiales médicos.");
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HistorialMedico().setVisible(true);
            }
        });
    }
}
