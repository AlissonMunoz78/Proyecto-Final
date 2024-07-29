import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Reporte extends JFrame {
    private JTextArea displayArea;

    public Reporte() {
        setTitle("Ver Reportes - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane);

        loadReports();
    }

    private void loadReports() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT especialidad, COUNT(*) AS cantidad FROM Citas GROUP BY especialidad";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Especialidad: ").append(rs.getString("especialidad")).append("\n");
                sb.append("Cantidad de citas: ").append(rs.getInt("cantidad")).append("\n\n");
            }

            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al recuperar los reportes.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Reporte().setVisible(true));
    }
}
