
public class ResultadoExamen {
    private int id;
    private Paciente paciente;
    private String resultado;

    public ResultadoExamen(int id, Paciente paciente, String resultado) {
        this.id = id;
        this.paciente = paciente;
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getResultado() {
        return resultado;
    }

    public void agregarResultado() {
        // Implementar lógica para agregar un resultado de examen
    }

    public void verResultado() {
        // Implementar lógica para ver el resultado de un examen
    }
}