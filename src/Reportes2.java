import database.ConexionBaseDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reportes2 extends JFrame {
    private JTextField campoCedula;
    private JButton botonEliminar;
    private JButton botonRegresar;
    private JPanel panelEliminar;

    public Reportes2() {
        setTitle("Eliminar Paciente");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelEliminar);
        setLocationRelativeTo(null);

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                eliminarPaciente(cedula);
            }
        });

        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });
    }

    private void eliminarPaciente(String cedula) {
        Connection connection = ConexionBaseDatos.getConnection();
        String query = "DELETE FROM PACIENTE WHERE cedula = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cedula);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Paciente eliminado con éxito");
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
