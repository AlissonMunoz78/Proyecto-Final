import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cita extends JFrame {
    private JButton registerAppointmentButton;
    private JButton viewAppointmentsButton;
    private JTextArea displayArea;

    public Cita() {
        // Configuración de la ventana principal
        setTitle("Registro de Citas - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        registerAppointmentButton = new JButton("Registrar Cita");
        viewAppointmentsButton = new JButton("Ver Citas");

        buttonPanel.add(registerAppointmentButton);
        buttonPanel.add(viewAppointmentsButton);

        // Área de visualización
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Agregar paneles al marco
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Acciones de los botones
        registerAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCita();
            }
        });

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCitas();
            }
        });
    }

    // Método para registrar una cita
    private void registrarCita() {
        JTextField pacienteIdField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField fechaCitaField = new JTextField();
        JTextField horaCitaField = new JTextField();

        Object[] message = {
                "ID del Paciente:", pacienteIdField,
                "ID del Doctor:", doctorIdField,
                "Fecha de la Cita (YYYY-MM-DD):", fechaCitaField,
                "Hora de la Cita (HH:MM):", horaCitaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String pacienteId = pacienteIdField.getText().trim();
            String doctorId = doctorIdField.getText().trim();
            String fechaCita = fechaCitaField.getText().trim();
            String horaCita = horaCitaField.getText().trim();

            if (!pacienteId.isEmpty() && !doctorId.isEmpty() && !fechaCita.isEmpty() && !horaCita.isEmpty()) {
                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement("INSERT INTO Citas (pacienteId, doctorId, fechaCita, horaCita) VALUES (?, ?, ?, ?)")) {
                    ps.setInt(1, Integer.parseInt(pacienteId));
                    ps.setInt(2, Integer.parseInt(doctorId));
                    ps.setString(3, fechaCita);
                    ps.setString(4, horaCita);
                    ps.executeUpdate();
                    displayArea.setText("Cita registrada exitosamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    displayArea.setText("Error al registrar cita.");
                }
            } else {
                displayArea.setText("Por favor, complete todos los campos.");
            }
        }
    }

    // Método para ver citas
    private void verCitas() {
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Citas");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Paciente ID: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Doctor ID: ").append(rs.getInt("doctorId")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fechaCita")).append("\n");
                sb.append("Hora: ").append(rs.getString("horaCita")).append("\n\n");
            }
            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener citas.");
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
        SwingUtilities.invokeLater(() -> new Cita().setVisible(true));
    }
}
