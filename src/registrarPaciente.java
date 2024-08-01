import database.ConexionBaseDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrarPaciente extends JFrame {
    private JTextField campoCedula;
    private JTextField campoHistorialClinico;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JTextField campoEdad;
    private JTextArea areaDescripcionEnfermedad;
    private JButton botonRegistrar;
    private JButton botonBuscar;
    private JPanel panelRegistrar;
    private JButton menúButton;

    public registrarPaciente() {
        setTitle("Registrar Paciente");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelRegistrar);
        setLocationRelativeTo(null);

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                int historialClinico;
                int edad;

                try {
                    historialClinico = Integer.parseInt(campoHistorialClinico.getText());
                    edad = Integer.parseInt(campoEdad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores válidos para el historial clínico y la edad.");
                    return;
                }

                String nombre = campoNombre.getText();
                String apellido = campoApellido.getText();
                String telefono = campoTelefono.getText();
                String descripcionEnfermedad = areaDescripcionEnfermedad.getText();

                if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || descripcionEnfermedad.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados.");
                } else {
                    registrarPaciente(cedula, historialClinico, nombre, apellido, telefono, edad, descripcionEnfermedad);
                }
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buscarpaciente().setVisible(true);
                dispose();
            }
        });

        menúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administrador().setVisible(true);
                dispose();
            }
        });
    }

    private void registrarPaciente(String cedula, int historialClinico, String nombre, String apellido, String telefono, int edad, String descripcionEnfermedad) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConexionBaseDatos.getConnection();
            String query = "INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cedula);
            preparedStatement.setInt(2, historialClinico);
            preparedStatement.setString(3, nombre);
            preparedStatement.setString(4, apellido);
            preparedStatement.setString(5, telefono);
            preparedStatement.setInt(6, edad);
            preparedStatement.setString(7, descripcionEnfermedad);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Paciente registrado con éxito");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el paciente. Inténtelo de nuevo.");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
