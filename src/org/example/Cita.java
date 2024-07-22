package org.example;

import java.util.Date;

public class Cita {
    private Date fecha;
    private Paciente paciente;
    private Medico medico;

    public Cita(Date fecha, Paciente paciente, Medico medico) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}