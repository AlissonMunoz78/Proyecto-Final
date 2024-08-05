import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistrarCita extends JFrame {
    private JPanel panelcita;
    private JTextField nombreBuscar;
    private JButton buscarbtn;
    private JTextField nombreDoc;
    private JTextField fechacita;
    private JTextField HoraCita;
    private JComboBox<String> comboBox1;
    private JButton guardarCitaButton;
    private JButton cancelarButton;
    private JLabel idpaciente;

    public RegistrarCita() {
        super("Registrar Cita");
        setContentPane(panelcita);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buscarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPaciente();
            }
        });

        guardarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCita();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Método para buscar paciente
    private void buscarPaciente() {
        String nombre = nombreBuscar.getText();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexion_base();
            String sql = "SELECT id FROM Pacientes WHERE nombre = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idpaciente.setText(String.valueOf(rs.getInt("id")));
            } else {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el paciente: " + ex.getMessage());
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

    // Método para guardar la cita
    private void guardarCita() {
        String fecha = fechacita.getText();
        String hora = HoraCita.getText();
        String detalles = nombreDoc.getText();
        String especialidad = (String) comboBox1.getSelectedItem();
        String pacienteId = idpaciente.getText();

        // Validar formato de fecha
        if (!validarFecha(fecha)) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.");
            return;
        }

        // Validar formato de hora
        if (!validarHora(hora)) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:MM:SS.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = conexion_base();
            String sql = "INSERT INTO Citas (paciente_id, medico_id, fecha, hora,  especialidad) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(pacienteId));
            stmt.setInt(2, 1);
            stmt.setDate(3, Date.valueOf(fecha));
            stmt.setTime(4, Time.valueOf(hora));
            stmt.setString(5, especialidad);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cita guardada exitosamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la cita: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    private boolean validarFecha(String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean validarHora(String hora) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setLenient(false);
        try {
            timeFormat.parse(hora);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    public static void main(String[] args) {
        new RegistrarCita();
    }
}
