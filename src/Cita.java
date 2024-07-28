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
        setTitle("Gestión de Citas - MediCare");
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

    // Método para registrar una nueva cita
    private void registrarCita() {
        JTextField patientIdField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField specialtyField = new JTextField();
        JTextField doctorIdField = new JTextField();

        Object[] fields = {
                "ID del Paciente:", patientIdField,
                "Fecha (YYYY-MM-DD):", dateField,
                "Especialidad:", specialtyField,
                "ID del Médico:", doctorIdField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Registrar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int patientId = Integer.parseInt(patientIdField.getText());
            String date = dateField.getText();
            String specialty = specialtyField.getText();
            int doctorId = Integer.parseInt(doctorIdField.getText());

            try {
                Connection con = getConnection();
                String query = "INSERT INTO Citas (pacienteId, fecha, especialidad, medicoId) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, patientId);
                ps.setString(2, date);
                ps.setString(3, specialty);
                ps.setInt(4, doctorId);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Cita registrada con éxito.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar la cita.");
            }
        }
    }

    // Método para ver las citas
    private void verCitas() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Citas";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Cita: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n");
                sb.append("Especialidad: ").append(rs.getString("especialidad")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener las citas.");
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
                new Cita().setVisible(true);
            }
        });
    }
}
