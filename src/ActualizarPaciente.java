import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ActualizarPaciente extends JFrame {

    private JPanel panelActualizarPaciente;
    private JTextField nombreBusquedaTextField; // Campo de búsqueda
    private JTextField nombreTextField;
    private JTextField edadTextField;
    private JTextField lugarNacimientoTextField;
    private JTextField generoTextField;
    private JTextField telefonoTextField;
    private JButton buscarButton;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JLabel idLabel;
    private JTextField idTextField;

    public ActualizarPaciente() {
        super("Actualizar Paciente");

        panelActualizarPaciente = new JPanel();
        panelActualizarPaciente.setLayout(new BorderLayout());

        // Campo y botón de búsqueda
        nombreBusquedaTextField = new JTextField(20);
        buscarButton = new JButton("Buscar");
        buscarButton.setPreferredSize(new Dimension(100, 30)); // Ajustar tamaño del botón

        JPanel busquedaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busquedaPanel.add(new JLabel("Buscar por nombre:"));
        busquedaPanel.add(nombreBusquedaTextField);
        busquedaPanel.add(buscarButton);

        // Campos para mostrar y editar información del paciente
        nombreTextField = new JTextField(20);
        edadTextField = new JTextField(20);
        lugarNacimientoTextField = new JTextField(20);
        generoTextField = new JTextField(20);
        telefonoTextField = new JTextField(20);
        guardarButton = new JButton("Guardar");
        cancelarButton = new JButton("Cancelar");

        idTextField = new JTextField(20);
        idTextField.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idTextField);
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nombreTextField);
        inputPanel.add(new JLabel("Edad:"));
        inputPanel.add(edadTextField);
        inputPanel.add(new JLabel("Lugar de Nacimiento:"));
        inputPanel.add(lugarNacimientoTextField);
        inputPanel.add(new JLabel("Género:"));
        inputPanel.add(generoTextField);
        inputPanel.add(new JLabel("Teléfono:"));
        inputPanel.add(telefonoTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        panelActualizarPaciente.add(busquedaPanel, BorderLayout.NORTH);
        panelActualizarPaciente.add(inputPanel, BorderLayout.CENTER);
        panelActualizarPaciente.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panelActualizarPaciente);
        setSize(600, 400); // Aumentar el tamaño de la ventana
        setResizable(true); // Permitir cambiar tamaño
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPaciente();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPaciente();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private Connection conexionBase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    private void buscarPaciente() {
        String nombreBusqueda = nombreBusquedaTextField.getText().trim();

        if (nombreBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del paciente a buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = conexionBase()) {
            String sql = "SELECT * FROM Pacientes WHERE nombres LIKE ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + nombreBusqueda + "%");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                nombreTextField.setText(rs.getString("nombres"));
                edadTextField.setText(String.valueOf(rs.getInt("edad")));
                lugarNacimientoTextField.setText(rs.getString("lugar_nacimiento"));
                generoTextField.setText(rs.getString("genero"));
                telefonoTextField.setText(rs.getString("telefono"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el paciente con el nombre: " + nombreBusqueda, "Información", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPaciente() {
        String id = idTextField.getText().trim();
        String nombres = nombreTextField.getText().trim();
        String edad = edadTextField.getText().trim();
        String lugarNacimiento = lugarNacimientoTextField.getText().trim();
        String genero = generoTextField.getText().trim();
        String telefono = telefonoTextField.getText().trim();

        if (id.isEmpty() || nombres.isEmpty() || edad.isEmpty() || lugarNacimiento.isEmpty() || genero.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = conexionBase()) {
            String sql = "UPDATE Pacientes SET nombres = ?, edad = ?, lugar_nacimiento = ?, genero = ?, telefono = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, nombres);
            pst.setInt(2, Integer.parseInt(edad));
            pst.setString(3, lugarNacimiento);
            pst.setString(4, genero);
            pst.setString(5, telefono);
            pst.setInt(6, Integer.parseInt(id));

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Paciente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el paciente a actualizar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        idTextField.setText("");
        nombreTextField.setText("");
        edadTextField.setText("");
        lugarNacimientoTextField.setText("");
        generoTextField.setText("");
        telefonoTextField.setText("");
    }

    public static void main(String[] args) {
        new ActualizarPaciente();
    }
}
