import java.sql.Date;
import java.sql.Time;

public class Cita {
    private int id;
    private Date fecha;
    private Time hora;
    private Paciente paciente;
    private PersonalMedico personalMedico;

    public Cita(int id, Date fecha, Time hora, Paciente paciente, PersonalMedico personalMedico) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.personalMedico = personalMedico;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public Time getHora() {
        return hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public PersonalMedico getPersonalMedico() {
        return personalMedico;
    }

    public void programarCita() {
        // Implementar lógica para programar una cita
    }

    public void cancelarCita() {
        // Implementar lógica para cancelar una cita
    }
}