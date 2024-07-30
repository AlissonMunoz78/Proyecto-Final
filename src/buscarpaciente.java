import database.ConexionBaseDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class buscarpaciente extends JFrame {
    private JTextField campoCedula;
    private JButton botonBuscar;
    private JButton botonRegresar;
    private JTextArea areaResultado;
    private JPanel panelBuscar;
    private JButton menúButton;

    public buscarpaciente() {
        setTitle("Buscar Paciente");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelBuscar);
        setLocationRelativeTo(null);

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                buscarPaciente(cedula);
            }
        });

        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registrarPaciente().setVisible(true);
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

    private void buscarPaciente(String cedula) {
        Connection connection = ConexionBaseDatos.getConnection();
        String query = "SELECT * FROM PACIENTE WHERE cedula = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cedula);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String resultado = "Cédula: " + resultSet.getString("cedula") +
                        "\nHistorial Clínico: " + resultSet.getInt("n_historial_clinico") +
                        "\nNombre: " + resultSet.getString("nombre") +
                        "\nApellido: " + resultSet.getString("apellido") +
                        "\nTeléfono: " + resultSet.getString("telefono") +
                        "\nEdad: " + resultSet.getInt("edad") +
                        "\nDescripción de Enfermedad: " + resultSet.getString("descripcion_enfermedad");

                areaResultado.setText(resultado);
            } else {
                areaResultado.setText("Paciente no encontrado");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
