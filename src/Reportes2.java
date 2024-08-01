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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

        public class Reporte extends JFrame {
            private JTextArea displayArea;
            private JButton refreshButton;

            public Reporte() {
                setTitle("Reportes Estadísticos - MediCare");
                setSize(600, 400);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setLocationRelativeTo(null);

                JPanel panel = new JPanel(new BorderLayout());
                add(panel);

                displayArea = new JTextArea();
                displayArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(displayArea);
                panel.add(scrollPane, BorderLayout.CENTER);

                refreshButton = new JButton("Actualizar Reportes");
                refreshButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loadReports();
                    }
                });
                panel.add(refreshButton, BorderLayout.SOUTH);

                loadReports(); // Carga los reportes al inicio
            }

            private void loadReports() {
                String query = "SELECT * FROM ReportesEstadisticos"; // Reemplaza con la consulta adecuada

                try (Connection con = ConexionBaseDatos.getConnection();
                     PreparedStatement ps = con.prepareStatement(query);
                     ResultSet rs = ps.executeQuery()) {

                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        sb.append("Reporte ID: ").append(rs.getInt("reporte_id")).append("\n");
                        sb.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
                        sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                        sb.append("Datos: ").append(rs.getString("datos")).append("\n\n");
                    }

                    displayArea.setText(sb.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    displayArea.setText("Error al recuperar los reportes.");
                    JOptionPane.showMessageDialog(this, "Error al recuperar los reportes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> new Reporte().setVisible(true));
            }
        }

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
