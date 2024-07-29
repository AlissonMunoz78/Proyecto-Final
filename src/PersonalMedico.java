import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonalMedico extends JFrame {
    private JButton viewScheduleButton;
    private JButton registerAppointmentButton;
    private JButton viewMedicalHistoryButton;
    private JButton viewExamResultsButton;
    private JButton viewTreatmentsButton;
    private JTextArea displayArea;

    public PersonalMedico() {
        setTitle("Personal Médico - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        viewScheduleButton = new JButton("Ver Horario");
        registerAppointmentButton = new JButton("Registrar Cita");
        viewMedicalHistoryButton = new JButton("Ver Historial Médico");
        viewExamResultsButton = new JButton("Ver Resultados Examen");
        viewTreatmentsButton = new JButton("Ver Tratamientos");

        buttonPanel.add(viewScheduleButton);
        buttonPanel.add(registerAppointmentButton);
        buttonPanel.add(viewMedicalHistoryButton);
        buttonPanel.add(viewExamResultsButton);
        buttonPanel.add(viewTreatmentsButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        viewScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHorario();
            }
        });

        registerAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCita();
            }
        });

        viewMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialMedico();
            }
        });

        viewExamResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verResultadosExamen();
            }
        });

        viewTreatmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verTratamientos();
            }
        });
    }

    private void verHorario() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Horarios WHERE medicoId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getMedicoId());
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Día: ").append(rs.getString("dia")).append("\n");
                sb.append("Hora Inicio: ").append(rs.getString("horaInicio")).append("\n");
                sb.append("Hora Fin: ").append(rs.getString("horaFin")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener el horario del médico.");
        }
    }

    private void registrarCita() {
        // Implementar la lógica para registrar una cita
        displayArea.setText("Registrar Cita - Funcionalidad no implementada.");
    }

    private void verHistorialMedico() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM HistorialesMedicos WHERE medicoId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getMedicoId());
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Historial: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener el historial médico.");
        }
    }

    private void verResultadosExamen() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM ResultadosExamenes WHERE medicoId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getMedicoId());
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Resultado: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Documento: ").append(rs.getString("documento")).append("\n");
                sb.append("Fecha: ").append(rs.getString("fecha")).append("\n\n");
            }

            displayArea.setText(sb.toString());

            rs.close();
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al obtener los resultados de exámenes.");
        }
    }

    private void verTratamientos() {
        try {
            Connection con = getConnection();
            String query = "SELECT * FROM Tratamientos WHERE medicoId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, getMedicoId());
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID Tratamiento: ").append(rs.getInt("id")).append("\n");
                sb.append("ID Paciente: ").append(rs.getInt("pacienteId")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Medicamento: ").append(rs.getString("medicamento")).append("\n");
                sb.append("Fecha Inicio: ").append(rs.getString("fechaInicio")).append("\n");
                sb.append("Fecha Fin: ").append(rs.getString("fechaFin")).append("\n\n");
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

    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    private int getMedicoId() {
        // Debería devolver el ID del médico autenticado
        // Esto es un ejemplo, cambiar según la implementación
        return 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PersonalMedico().setVisible(true);
        });
    }
}
