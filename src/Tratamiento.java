public class Tratamiento {
    private Paciente paciente;
    private String descripcion;

    public Tratamiento(Paciente paciente, String descripcion) {
        this.paciente = paciente;
        this.descripcion = descripcion;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
