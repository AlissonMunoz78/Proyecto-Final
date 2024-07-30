import database.ConexionBaseDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class buscarmedico extends JFrame {
    private JTextField campoCedula;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JTextField campoEdad;
    private JTextArea areaDescripcionEnfermedad;
    private JButton botonActualizar;
    private JButton botonRegresar;
    private JPanel panelActualizar;
    private JButton botonBuscar;
    private JTextArea areaResultado;
    private JButton menúButton;
    private JComboBox comboBox1;

    public buscarmedico() {
        setTitle("Actualizar Paciente");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelActualizar);
        setLocationRelativeTo(null);

        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                String nombre = campoNombre.getText();
                String apellido = campoApellido.getText();
                String telefono = campoTelefono.getText();
                int edad = Integer.parseInt(campoEdad.getText());
                String descripcionEnfermedad = areaDescripcionEnfermedad.getText();

                actualizarPaciente(cedula, nombre, apellido, telefono, edad, descripcionEnfermedad);
            }
        });

        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administrador().setVisible(true);
                dispose();
            }
        });
    }

    private void actualizarPaciente(String cedula, String nombre, String apellido, String telefono, int edad, String descripcionEnfermedad) {
        Connection connection = ConexionBaseDatos.getConnection();
        String query = "UPDATE PACIENTE SET nombre = ?, apellido = ?, telefono = ?, edad = ?, descripcion_enfermedad = ? WHERE cedula = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, telefono);
            preparedStatement.setInt(4, edad);
            preparedStatement.setString(5, descripcionEnfermedad);
            preparedStatement.setString(6, cedula);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Paciente actualizado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Paciente no encontrado");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
