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
    private JTextField horarioMananaTextField;
    private JTextField horarioTardeTextField;
    private JTextField nombreBuscarTextField;
    private JButton buscarButton;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton buscarPorNombreButton;

    public ActualizarDoctor() {
        super("Actualizar Doctor");

        panelActualizarDoctor = new JPanel();
        panelActualizarDoctor.setLayout(new BorderLayout());

        // Componentes de búsqueda
        nombreBuscarTextField = new JTextField(20);
        buscarPorNombreButton = new JButton("Buscar por Nombre");
        buscarPorNombreButton.setPreferredSize(new Dimension(150, 30));

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Nombre:"));
        searchPanel.add(nombreBuscarTextField);
        searchPanel.add(buscarPorNombreButton);

        // Componentes de edición
        idTextField = new JTextField(20);
        nombresTextField = new JTextField(20);
        especialidadTextField = new JTextField(20);
        horarioMananaTextField = new JTextField(20);
        horarioTardeTextField = new JTextField(20);
        buscarButton = new JButton("Buscar");
        buscarButton.setPreferredSize(new Dimension(100, 30));
        guardarButton = new JButton("Guardar");
        cancelarButton = new JButton("Cancelar");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idTextField);
        inputPanel.add(new JLabel("Nombres:"));
        inputPanel.add(nombresTextField);
        inputPanel.add(new JLabel("Especialidad:"));
        inputPanel.add(especialidadTextField);
        inputPanel.add(new JLabel("Horario Mañana:"));
        inputPanel.add(horarioMananaTextField);
        inputPanel.add(new JLabel("Horario Tarde:"));
        inputPanel.add(horarioTardeTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buscarButton);
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);

        panelActualizarDoctor.add(searchPanel, BorderLayout.NORTH);
        panelActualizarDoctor.add(inputPanel, BorderLayout.CENTER);
        panelActualizarDoctor.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panelActualizarDoctor);
        setSize(600, 500); // Ajuste del tamaño de la ventana
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configurar los listeners de los botones
        buscarPorNombreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarDoctorPorNombre();
            }
        });

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

    private void buscarDoctorPorNombre() {
        String nombre = nombreBuscarTextField.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del doctor a buscar.");
            return;
        }

        try (Connection connection = conexionBase()) {
            String sql = "SELECT * FROM Medicos WHERE nombres LIKE ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + nombre + "%");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                nombresTextField.setText(rs.getString("nombres"));
                especialidadTextField.setText(rs.getString("especialidad"));
                horarioMananaTextField.setText(rs.getString("horario_mañana"));
                horarioTardeTextField.setText(rs.getString("horario_tarde"));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el doctor con el nombre especificado.");
                limpiarCampos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el doctor: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        idTextField.setText("");
        nombresTextField.setText("");
        especialidadTextField.setText("");
        horarioMananaTextField.setText("");
        horarioTardeTextField.setText("");
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
        String horarioManana = horarioMananaTextField.getText().trim();
        String horarioTarde = horarioTardeTextField.getText().trim();

        if (nombres.isEmpty() || especialidad.isEmpty() || horarioManana.isEmpty() || horarioTarde.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        try (Connection connection = conexionBase()) {
            String sql = "UPDATE Medicos SET nombres = ?, especialidad = ?, horario_mañana = ?, horario_tarde = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, nombres);
            pst.setString(2, especialidad);
            pst.setString(3, horarioManana);
            pst.setString(4, horarioTarde);
            pst.setInt(5, id);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Doctor actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el doctor con el ID especificado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el doctor: " + ex.getMessage());
        }
    }

    private Connection conexionBase() throws SQLException {
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
