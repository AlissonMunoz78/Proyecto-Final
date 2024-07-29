import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Tratamiento extends JFrame {
    private JButton registerTreatmentButton;
    private JButton viewTreatmentsButton;
    private JTextArea displayArea;

    public Tratamiento() {
        setTitle("Gestión de Tratamientos - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        registerTreatmentButton = new JButton("Registrar Tratamiento");
        viewTreatmentsButton = new JButton("Ver Tratamientos");

        buttonPanel.add(registerTreatmentButton);
        buttonPanel.add(viewTreatmentsButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        registerTreatmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarTratamiento();
            }
        });

        viewTreatmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verTratamientos();
            }
        });
    }

    private void registrarTratamiento() {
        JTextField patientIdField = new JTextField();
        JTextField treatmentNameField = new JTextField();
        JTextField medicationField = new JTextField();
        JTextField doctorIdField = new JTextField();

        Object[] fields = {
                "ID del Paciente:", patientIdField,
                "Nombre del Tratamiento:", treatmentNameField,
                "Medicamento:", medicationField,
                "ID del Médico:", doctorIdField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Registrar Tratamiento", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int patientId = Integer.parseInt(patientIdField.getText());
            String treatmentName = treatmentNameField.getText();
            String medication = medicationField.getText();
            int doctorId = Integer.parseInt(doctorIdField.getText());

            try {
                Connection con = getConnection();
                String query = "INSERT INTO Tratamientos (pacienteId, nombreTratamiento, medicamento, medicoId, fechaInicio) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, patientId);
                ps.setString(2, treatmentName);
                ps.setString(3, medication);
                ps.setInt(4, doctorId);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Tratamiento registrado con éxito.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar el tratamiento.");
            }
        }
    }

    private void verTratamientos() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Tratamientos";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Tratamiento: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Nombre del Tratamiento: ").append(rs.getString("nombreTratamiento")).append("\n");
                sb.append("Medicamento: ").append(rs.getString("medicamento")).append("\n");
                sb.append("ID Médico: ").append(rs.getInt("medicoId")).append("\n");
                sb.append("Fecha de Inicio: ").append(rs.getString("fechaInicio")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los tratamientos.");
        }
    }

    public Connection getConnection() throws Exception {
        String URL = "jdbc:mysql://localhost:3306/proyectofinal";
        String USER = "root";
        String PASSWORD = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tratamiento().setVisible(true);
            }
        });
    }
}
