package org.example;

public class ResultadoExamen {
    private String nombreExamen;
    private String resultado;

    public ResultadoExamen(String nombreExamen, String resultado) {
        this.nombreExamen = nombreExamen;
        this.resultado = resultado;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}