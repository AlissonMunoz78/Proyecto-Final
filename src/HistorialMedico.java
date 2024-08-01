import database.ConexionBaseDatos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HistorialMedico extends JFrame {
    private Usuario medico;
    private JTextArea displayArea;

    public HistorialMedico(Usuario medico) {
        this.medico = medico;

        setTitle("Historial Médico - MediCare");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane);

        loadMedicalHistory();
    }

    private void loadMedicalHistory() {
        String query = "SELECT * FROM HistorialMedico WHERE medico_id = ?";

        try (Connection con = ConexionBaseDatos.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, medico.getId());
            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder sb = new StringBuilder();
                boolean hasResults = false;

                while (rs.next()) {
                    sb.append("ID del Paciente: ").append(rs.getInt("paciente_id")).append("\n");
                    sb.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
                    sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n\n");
                    hasResults = true;
                }

                if (!hasResults) {
                    sb.append("No se encontraron registros.");
                }

                displayArea.setText(sb.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            displayArea.setText("Error al recuperar el historial médico.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario medico = new Usuario(2, "medico", "medico123", "medico");
            new HistorialMedico(medico).setVisible(true);
        });
    }
}
