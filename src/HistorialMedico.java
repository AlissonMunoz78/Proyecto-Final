import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HistorialMedico extends JFrame {
    private JPanel panelHistorial;
    private JTextField nombreBuscar;
    private JButton buscar;
    private JButton salir;
    private JTextArea descripcion; // Área de texto para mostrar el historial médico
    private JLabel npaciente; // Etiqueta para mostrar el nombre del paciente
    private JLabel hpaciente; // Etiqueta para mostrar información adicional (puede ser opcional)

    public HistorialMedico() {
        super("Historial Médico");
        setContentPane(panelHistorial);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarHistorial();
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Método para buscar historial médico
    private void buscarHistorial() {
        String nombre = nombreBuscar.getText().trim();

        // Verificar si el campo está vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del paciente.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder resultados = new StringBuilder();

        try {
            conn = conexion_base();
            // Consulta para obtener el paciente_id
            String sqlPaciente = "SELECT id FROM Pacientes WHERE nombres = ?";
            stmt = conn.prepareStatement(sqlPaciente);
            stmt.setString(1, nombre);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int pacienteId = rs.getInt("id");

                // Limpiar etiquetas
                npaciente.setText("Nombre del Paciente: " + nombre);
                hpaciente.setText(""); // Limpiar información adicional anterior

                // Limpiar área de texto antes de agregar nuevos resultados
                descripcion.setText("");

                // Consulta para obtener el historial médico del paciente por paciente_id
                String sqlHistorial = "SELECT d.nombre AS doctor_nombre, h.descripcion AS enfermedad " +
                        "FROM HistorialMedico h " +
                        "JOIN Medicos d ON h.medico_id = d.id " +
                        "WHERE h.paciente_id = ?";
                stmt = conn.prepareStatement(sqlHistorial);
                stmt.setInt(1, pacienteId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String doctorNombre = rs.getString("doctor_nombre");
                    String enfermedadDescripcion = rs.getString("enfermedad");
                    resultados.append("Doctor: ").append(doctorNombre).append("\n");
                    resultados.append("Descripción: ").append(enfermedadDescripcion).append("\n\n");
                }

                if (resultados.length() == 0) {
                    resultados.append("No se encontraron registros.");
                }

                descripcion.setText(resultados.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el paciente con el nombre ingresado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el historial médico: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    public static void main(String[] args) {
        new HistorialMedico();
    }
}
