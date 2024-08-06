import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ActualizarDoctor extends JFrame {
    private JPanel panelActualizarDoctor;
    private JTextField idTextField;
    private JTextField nombresTextField;
    private JTextField especialidadTextField;
    private JTextField horarioTextField;
    private JButton guardarButton;
    private JButton cancelarButton;

    public ActualizarDoctor() {
        super("Actualizar Doctor");

        // Inicializar el panel manualmente
        panelActualizarDoctor = new JPanel();
        panelActualizarDoctor.setLayout(new BorderLayout());

        idTextField = new JTextField(20);
        nombresTextField = new JTextField(20);
        especialidadTextField = new JTextField(20);
        horarioTextField = new JTextField(20);
        guardarButton = new JButton("Guardar");
        cancelarButton = new JButton("Cancelar");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idTextField);
        inputPanel.add(new JLabel("Nombres:"));
        inputPanel.add(nombresTextField);
        inputPanel.add(new JLabel("Especialidad:"));
        inputPanel.add(especialidadTextField);
        inputPanel.add(new JLabel("Horario:"));
        inputPanel.add(horarioTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        panelActualizarDoctor.add(inputPanel, BorderLayout.CENTER);
        panelActualizarDoctor.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panelActualizarDoctor);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configurar los listeners de los botones
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDoctor();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void actualizarDoctor() {
        int id;
        try {
            id = Integer.parseInt(idTextField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número entero.");
            return;
        }

        String nombres = nombresTextField.getText().trim();
        String especialidad = especialidadTextField.getText().trim();
        String horario = horarioTextField.getText().trim();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = conexion_base();
            String sql = "UPDATE Medicos SET nombres = ?, especialidad = ?, horario = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombres);
            stmt.setString(2, especialidad);
            stmt.setString(3, horario);
            stmt.setInt(4, id);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Doctor actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un doctor con el ID especificado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el doctor: " + ex.getMessage());
        } finally {
            try {
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
        SwingUtilities.invokeLater(() -> {
            new ActualizarDoctor().setVisible(true);
        });
    }
}
