public class Tratamiento {
    private int id;
    private int pacienteId;
    private String descripcion;
    private String medicamento;

    // Constructor
    public Tratamiento(int id, int pacienteId, String descripcion, String medicamento) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.descripcion = descripcion;
        this.medicamento = medicamento;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }
}
