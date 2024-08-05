import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ResultadoExamenes {
    private JButton salirButton;
    private JTextField npaciente;
    private JButton buscarButton;
    private JTextArea resultados;
    private JPanel panelresultados;

    public ResultadoExamenes() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarResultados();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void buscarResultados() {
        String nombre = npaciente.getText().trim();

        // Verificar si el campo está vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del paciente.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder resultadosTexto = new StringBuilder();

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
                resultados.setText("");

                // Consulta para obtener los resultados de exámenes del paciente por paciente_id
                String sqlResultados = "SELECT d.nombre AS doctor_nombre, r.descripcion AS resultado " +
                        "FROM ResultadosExamen r " +
                        "JOIN Medicos d ON r.medico_id = d.id " +
                        "WHERE r.paciente_id = ?";
                stmt = conn.prepareStatement(sqlResultados);
                stmt.setInt(1, pacienteId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String doctorNombre = rs.getString("doctor_nombre");
                    String resultadoDescripcion = rs.getString("resultado");
                    resultadosTexto.append("Doctor: ").append(doctorNombre).append("\n");
                    resultadosTexto.append("Resultado: ").append(resultadoDescripcion).append("\n\n");
                }

                if (resultadosTexto.length() == 0) {
                    resultadosTexto.append("No se encontraron resultados.");
                }

                resultados.setText(resultadosTexto.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el paciente con el nombre ingresado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar los resultados de exámenes: " + ex.getMessage());
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
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelresultados);
        if (frame != null) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Resultado de Exámenes");
        frame.setContentPane(new ResultadoExamenes().panelresultados);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
