package org.example;

import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private List<ResultadoExamen> resultadosExamen;
    private List<Tratamiento> tratamientos;

    public HistorialMedico() {
        this.resultadosExamen = new ArrayList<>();
        this.tratamientos = new ArrayList<>();
    }

    public List<ResultadoExamen> getResultadosExamen() {
        return resultadosExamen;
    }

    public void addResultadoExamen(ResultadoExamen resultadoExamen) {
        this.resultadosExamen.add(resultadoExamen);
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void addTratamiento(Tratamiento tratamiento) {
        this.tratamientos.add(tratamiento);
    }
}
