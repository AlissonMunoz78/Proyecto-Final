import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonalMedico extends JFrame {
    private JButton registerAppointmentButton;
    private JButton registerMedicalHistoryButton;
    private JButton registerExamResultButton;
    private JButton assignTreatmentButton;
    private JTextArea displayArea;

    public PersonalMedico() {
        // Configuración de la ventana principal
        setTitle("Personal Médico - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        registerAppointmentButton = new JButton("Registrar Cita");
        registerMedicalHistoryButton = new JButton("Registrar Historial Médico");
        registerExamResultButton = new JButton("Registrar Resultado Examen");
        assignTreatmentButton = new JButton("Asignar Tratamiento");

        buttonPanel.add(registerAppointmentButton);
        buttonPanel.add(registerMedicalHistoryButton);
        buttonPanel.add(registerExamResultButton);
        buttonPanel.add(assignTreatmentButton);

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

        registerMedicalHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarHistorialMedico();
            }
        });

        registerExamResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarResultadoExamen();
            }
        });

        assignTreatmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarTratamiento();
            }
        });
    }

    // Método para registrar una cita
    private void registrarCita() {
        JTextField pacienteIdField = new JTextField();
        JTextField fechaField = new JTextField();
        JTextField especialidadField = new JTextField();
        JTextField medicoIdField = new JTextField();

        Object[] message = {
                "ID Paciente:", pacienteIdField,
                "Fecha (YYYY-MM-DD):", fechaField,
                "Especialidad:", especialidadField,
                "ID Médico:", medicoIdField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int pacienteId = Integer.parseInt(pacienteIdField.getText());
                String fecha = fechaField.getText();
                String especialidad = especialidadField.getText();
                int medicoId = Integer.parseInt(medicoIdField.getText());

                Connection con = getConnection();
                String query = "INSERT INTO Citas (pacienteId, fecha, especialidad, medicoId) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, pacienteId);
                ps.setString(2, fecha);
                ps.setString(3, especialidad);
                ps.setInt(4, medicoId);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Cita registrada exitosamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar cita.");
            }
        }
    }

    // Método para registrar historial médico
    private void registrarHistorialMedico() {
        JTextField pacienteIdField = new JTextField();
        JTextField medicoIdField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField fechaField = new JTextField();

        Object[] message = {
                "ID Paciente:", pacienteIdField,
                "ID Médico:", medicoIdField,
                "Descripción:", descripcionField,
                "Fecha (YYYY-MM-DD):", fechaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Historial Médico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int pacienteId = Integer.parseInt(pacienteIdField.getText());
                int medicoId = Integer.parseInt(medicoIdField.getText());
                String descripcion = descripcionField.getText();
                String fecha = fechaField.getText();

                Connection con = getConnection();
                String query = "INSERT INTO HistorialesMedicos (pacienteId, medicoId, descripcion, fecha) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, pacienteId);
                ps.setInt(2, medicoId);
                ps.setString(3, descripcion);
                ps.setString(4, fecha);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Historial médico registrado exitosamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar historial médico.");
            }
        }
    }

    // Método para registrar resultado de examen
    private void registrarResultadoExamen() {
        JTextField pacienteIdField = new JTextField();
        JTextField medicoIdField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField documentoField = new JTextField();
        JTextField fechaField = new JTextField();

        Object[] message = {
                "ID Paciente:", pacienteIdField,
                "ID Médico:", medicoIdField,
                "Descripción:", descripcionField,
                "Documento (ruta):", documentoField,
                "Fecha (YYYY-MM-DD):", fechaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Resultado Examen", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int pacienteId = Integer.parseInt(pacienteIdField.getText());
                int medicoId = Integer.parseInt(medicoIdField.getText());
                String descripcion = descripcionField.getText();
                String documento = documentoField.getText();
                String fecha = fechaField.getText();

                Connection con = getConnection();
                String query = "INSERT INTO ResultadosExamenes (pacienteId, medicoId, descripcion, documento, fecha) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, pacienteId);
                ps.setInt(2, medicoId);
                ps.setString(3, descripcion);
                ps.setString(4, documento);
                ps.setString(5, fecha);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Resultado de examen registrado exitosamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al registrar resultado de examen.");
            }
        }
    }

    // Método para asignar tratamiento
    private void asignarTratamiento() {
        JTextField pacienteIdField = new JTextField();
        JTextField medicoIdField = new JTextField();
        JTextField descripcionField = new JTextField();
        JTextField medicamentoField = new JTextField();
        JTextField fechaInicioField = new JTextField();
        JTextField fechaFinField = new JTextField();

        Object[] message = {
                "ID Paciente:", pacienteIdField,
                "ID Médico:", medicoIdField,
                "Descripción:", descripcionField,
                "Medicamento:", medicamentoField,
                "Fecha Inicio (YYYY-MM-DD):", fechaInicioField,
                "Fecha Fin (YYYY-MM-DD):", fechaFinField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Asignar Tratamiento", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int pacienteId = Integer.parseInt(pacienteIdField.getText());
                int medicoId = Integer.parseInt(medicoIdField.getText());
                String descripcion = descripcionField.getText();
                String medicamento = medicamentoField.getText();
                String fechaInicio = fechaInicioField.getText();
                String fechaFin = fechaFinField.getText();

                Connection con = getConnection();
                String query = "INSERT INTO Tratamientos (pacienteId, medicoId, descripcion, medicamento, fechaInicio, fechaFin) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, pacienteId);
                ps.setInt(2, medicoId);
                ps.setString(3, descripcion);
                ps.setString(4, medicamento);
                ps.setString(5, fechaInicio);
                ps.setString(6, fechaFin);
                ps.executeUpdate();

                ps.close();
                con.close();

                displayArea.setText("Tratamiento asignado exitosamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                displayArea.setText("Error al asignar tratamiento.");
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PersonalMedico().setVisible(true);
            }
        });
    }
}
