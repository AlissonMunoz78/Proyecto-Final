import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Tratamiento {
    private JButton salir;
    private JButton buscar;
    private JTextArea desglose;
    private JPanel paneltratamiento;
    private JTextField npaciente; // Cambié JLabel a JTextField para ingresar el nombre del paciente

    public Tratamiento() {
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTratamiento();
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void buscarTratamiento() {
        String nombre = npaciente.getText().trim();

        // Verificar si el campo está vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del paciente.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder tratamientoTexto = new StringBuilder();

        try {
            conn = conexion_base();
            // Consulta para obtener el paciente_id
            String sqlPaciente = "SELECT id FROM Pacientes WHERE nombres = ?";
            stmt = conn.prepareStatement(sqlPaciente);
            stmt.setString(1, nombre);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int pacienteId = rs.getInt("id");

                // Limpiar área de texto antes de agregar nuevos resultados
                desglose.setText("");

                // Consulta para obtener los tratamientos del paciente por paciente_id
                String sqlTratamiento = "SELECT t.descripcion AS tratamiento, d.nombre AS doctor_nombre " +
                        "FROM Tratamientos t " +
                        "JOIN Medicos d ON t.medico_id = d.id " +
                        "WHERE t.paciente_id = ?";
                stmt = conn.prepareStatement(sqlTratamiento);
                stmt.setInt(1, pacienteId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String doctorNombre = rs.getString("doctor_nombre");
                    String tratamientoDescripcion = rs.getString("tratamiento");
                    tratamientoTexto.append("Doctor: ").append(doctorNombre).append("\n");
                    tratamientoTexto.append("Tratamiento: ").append(tratamientoDescripcion).append("\n\n");
                }

                if (tratamientoTexto.length() == 0) {
                    tratamientoTexto.append("No se encontraron tratamientos.");
                }

                desglose.setText(tratamientoTexto.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el paciente con el nombre ingresado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el tratamiento: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    private void dispose() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(paneltratamiento);
        if (frame != null) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tratamiento");
        frame.setContentPane(new Tratamiento().paneltratamiento);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
