import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tratamiento {
    private JTextField nombrebuscar;
    private JTextArea desglose;
    private JButton salir;
    private JButton buscar;
    private JLabel npacientes;
    private JLabel tratamiento;

    public Tratamiento() {
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
