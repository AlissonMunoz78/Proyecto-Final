import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Reportes extends JFrame {
    private JPanel panelReportes;
    private JButton verCitas;
    private JButton verDoctores;
    private JButton salir;

    public Reportes() {
        super("Reportes");
        setContentPane(panelReportes);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        verCitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGraficoCitas();
            }
        });

        verDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGraficoDoctores();
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Desea Salir del Sistema?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private void mostrarGraficoCitas() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = conexion_base()) {
            String sql = "SELECT fecha, COUNT(*) as total FROM Citas GROUP BY fecha";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String fecha = rs.getString("fecha");
                int total = rs.getInt("total");
                dataset.addValue(total, "Citas", fecha);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Citas Registradas",
                    "Fecha",
                    "Número de Citas",
                    dataset
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 400));
            setContentPane(chartPanel);
            revalidate();
            repaint();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos de citas: " + ex.getMessage());
        }
    }

    private void mostrarGraficoDoctores() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = conexion_base()) {
            String sql = "SELECT especialidad, COUNT(*) as total FROM Medicos GROUP BY especialidad";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String especialidad = rs.getString("especialidad");
                int total = rs.getInt("total");
                dataset.addValue(total, "Doctores", especialidad);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Doctores por Especialidad",
                    "Especialidad",
                    "Número de Doctores",
                    dataset
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 400));
            setContentPane(chartPanel);
            revalidate();
            repaint();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos de doctores: " + ex.getMessage());
        }
    }

    private Connection conexion_base() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medicare";
        String usuarioBD = "root";
        String contraseña = "123456";
        return DriverManager.getConnection(url, usuarioBD, contraseña);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Reportes().setVisible(true);
        });
    }
}
