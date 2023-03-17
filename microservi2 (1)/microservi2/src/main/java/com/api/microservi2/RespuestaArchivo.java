package com.api.microservi2;

import com.api.microservi2.CapRepository.CapaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class RespuestaArchivo {


    private int LineasValido;
    private int LineasInvalidas;


    public RespuestaArchivo(int lineasValido, int lineasInvalidas) {
        LineasValido = lineasValido;
        LineasInvalidas = lineasInvalidas;
    }


    public String Respuesta(){
        return "Lineas validas :"+ this.LineasValido+
        "Lineas Invalidas :" + this.LineasInvalidas;
    }

}
