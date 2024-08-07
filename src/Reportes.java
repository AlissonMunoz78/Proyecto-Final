import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reportes extends JFrame {
    private JButton verCitas;
    private JButton verDoctores;
    private JButton salir;
    private JPanel panelReportes;
    private JPanel chartPanel;

    public Reportes() {
        setTitle("Reportes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelReportes);
        chartPanel.setLayout(new BorderLayout());

        verCitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCitasChart();
            }
        });

        verDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDoctoresChart();
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void displayCitasChart() {
        chartPanel.removeAll();
        chartPanel.add(new CitasChartPanel(), BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private void displayDoctoresChart() {
        chartPanel.removeAll();
        chartPanel.add(new DoctoresChartPanel(), BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Reportes reportes = new Reportes();
            reportes.setVisible(true);
        });
    }
}

class CitasChartPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        String query = "SELECT especialidad, COUNT(*) as cantidad FROM Citas GROUP BY especialidad";
        List<Color> colors = List.of(Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicare", "root", "123456");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            int x = 50;
            int y = getHeight() - 50;
            int barWidth = 50;
            int maxHeight = getHeight() - 100;
            int spaceBetweenBars = 40;
            int colorIndex = 0;
            List<String> especialidades = new ArrayList<>();
            List<Color> usedColors = new ArrayList<>();

            while (rs.next()) {
                String especialidad = rs.getString("especialidad");
                int cantidad = rs.getInt("cantidad");
                int barHeight = (int) ((double) cantidad / 10 * maxHeight);

                Color barColor = colors.get(colorIndex % colors.size());
                g2.setColor(barColor);
                g2.fillRect(x, y - barHeight, barWidth, barHeight);

                g2.setColor(Color.BLACK);
                g2.drawRect(x, y - barHeight, barWidth, barHeight);

                // Mostrar la cantidad en lugar del nombre de la especialidad
                g2.drawString(String.valueOf(cantidad), x, y - barHeight - 5); // Ajusta la posición según sea necesario

                x += barWidth + spaceBetweenBars;
                especialidades.add(especialidad);
                usedColors.add(barColor);
                colorIndex++;
            }

            // Añadir leyenda
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            int legendX = 50;
            int legendY = 50;
            int legendBoxSize = 20;

            for (int i = 0; i < especialidades.size(); i++) {
                g2.setColor(usedColors.get(i));
                g2.fillRect(legendX, legendY + i * (legendBoxSize + 5), legendBoxSize, legendBoxSize);

                g2.setColor(Color.BLACK);
                g2.drawString(especialidades.get(i), legendX + legendBoxSize + 5, legendY + i * (legendBoxSize + 5) + legendBoxSize - 5);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

class DoctoresChartPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        String query = "SELECT especialidad, COUNT(*) as cantidad FROM Medicos GROUP BY especialidad";
        List<Color> colors = List.of(Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicare", "root", "123456");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            int total = 0;
            List<Integer> quantities = new ArrayList<>();
            List<String> especialidades = new ArrayList<>();

            while (rs.next()) {
                String especialidad = rs.getString("especialidad");
                int cantidad = rs.getInt("cantidad");
                especialidades.add(especialidad);
                quantities.add(cantidad);
                total += cantidad;
            }

            if (total == 0) return; // No data to display

            int x = getWidth() / 2;
            int y = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 3;
            int startAngle = 0;
            int colorIndex = 0;

            for (int i = 0; i < quantities.size(); i++) {
                int arcAngle = (int) Math.round(360.0 * quantities.get(i) / total);
                g2.setColor(colors.get(colorIndex % colors.size()));
                g2.fillArc(x - radius, y - radius, 2 * radius, 2 * radius, startAngle, arcAngle);

                // Dibujar etiquetas con cantidad
                int middleAngle = startAngle + arcAngle / 2;
                int labelX = (int) (x + (radius + 20) * Math.cos(Math.toRadians(middleAngle)));
                int labelY = (int) (y - (radius + 20) * Math.sin(Math.toRadians(middleAngle)));
                g2.setColor(Color.BLACK);
                g2.drawString(quantities.get(i) + "", labelX, labelY);

                startAngle += arcAngle;
                colorIndex++;
            }

            // Añadir leyenda
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            int legendX = 50;
            int legendY = getHeight() - 200;
            int legendBoxSize = 20;

            for (int i = 0; i < especialidades.size(); i++) {
                g2.setColor(colors.get(i % colors.size()));
                g2.fillRect(legendX, legendY + i * (legendBoxSize + 5), legendBoxSize, legendBoxSize);

                g2.setColor(Color.BLACK);
                g2.drawString(especialidades.get(i), legendX + legendBoxSize + 5, legendY + i * (legendBoxSize + 5) + legendBoxSize - 5);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
