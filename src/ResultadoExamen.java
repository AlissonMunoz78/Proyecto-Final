import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultadoExamen extends JFrame {
    private JTextArea displayArea;

    public ResultadoExamen() {
        setTitle("Ver Resultados de Exámenes - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane);

        loadExamResults();
    }

    private void loadExamResults() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM ResultadosExamen";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID del Paciente: ").append(rs.getInt("paciente_id")).append("\n");
                sb.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
                sb.append("Resultado: ").append(rs.getString("resultado")).append("\n\n");
            }

            displayArea.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al recuperar los resultados de los exámenes.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResultadoExamen().setVisible(true));
    }
}
