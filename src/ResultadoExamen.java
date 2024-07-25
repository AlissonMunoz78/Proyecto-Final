import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultadoExamen {
    private Paciente paciente;
    private String tratamiento;
    private ArrayList<String> exámenesRealizados;
    private Map<String, String> medicamentos;

    public ResultadoExamen(Paciente paciente, String tratamiento) {
        this.paciente = paciente;
        this.tratamiento = tratamiento;
        this.exámenesRealizados = new ArrayList<>();
        this.medicamentos = new HashMap<>();
    }

    public void agregarExamen(String examen) {
        exámenesRealizados.add(examen);
    }

    public void agregarMedicamento(String medicamento, String dosis) {
        medicamentos.put(medicamento, dosis);
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public ArrayList<String> getExámenesRealizados() {
        return exámenesRealizados;
    }

    public Map<String, String> getMedicamentos() {
        return medicamentos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Paciente: ").append(paciente.getNombre()).append("\n");
        sb.append("Tratamiento: ").append(tratamiento).append("\n");
        sb.append("Exámenes Realizados:\n");
        for (String examen : exámenesRealizados) {
            sb.append("- ").append(examen).append("\n");
        }
        sb.append("Medicamentos:\n");
        for (Map.Entry<String, String> entry : medicamentos.entrySet()) {
            sb.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
