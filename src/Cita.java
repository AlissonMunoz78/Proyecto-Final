import java.util.Date;

public class Cita {
    private Date fecha;
    private Paciente paciente;
    private PersonalMedico personalMedico;

    public Cita(Date fecha, Paciente paciente, PersonalMedico personalMedico) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.personalMedico = personalMedico;
    }

    public Date getFecha() {
        return fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public PersonalMedico getPersonalMedico() {
        return personalMedico;
    }
}
