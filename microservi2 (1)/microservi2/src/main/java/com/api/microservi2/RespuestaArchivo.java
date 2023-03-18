package com.api.microservi2;

import com.api.microservi2.CapRepository.CapaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class RespuestaArchivo {

    private Archivo archivo;
    private int LineasValido;
    private int LineasInvalidas;

    public RespuestaArchivo(Archivo archivo, int lineasValido, int lineasInvalidas) {
        this.archivo = archivo;
        LineasValido = lineasValido;
        LineasInvalidas = lineasInvalidas;
    }

    public String Respuesta(){
        return "Tipo archivo : "+ this.archivo.getTipo()+  " Lineas validas : "+ this.LineasValido+
        "  Lineas Invalidas : " + this.LineasInvalidas ;
    }

}
