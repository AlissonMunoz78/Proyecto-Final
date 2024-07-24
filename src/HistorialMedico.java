import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private int id;
    private Paciente paciente;
    private List<String> historial;

    public HistorialMedico(Paciente paciente) {
        this.id = paciente.getId();
        this.paciente = paciente;
        this.historial = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public List<String> getHistorial() {
        return historial;
    }

    public void agregarRegistro(String registro) {
        historial.add(registro);
    }

    public void verHistorial() {
        // Implementar lógica para ver el historial médico
    }
}