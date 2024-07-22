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
}