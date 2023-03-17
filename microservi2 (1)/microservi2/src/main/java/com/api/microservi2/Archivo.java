package com.api.microservi2;

public class Archivo {

    private String ruta;
    private String tipo;

    public Archivo(String ruta, String tipo) {
        this.ruta = ruta;
        this.tipo = tipo;
    }

    public String getRuta() {
        return ruta;
    }

    public String getTipo() {
        return tipo;
    }
}
