package com.example.microValidador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class Controller {


    private CapaValidador capaValidador;
    @Autowired
    public Controller(CapaValidador capaValidador) {
        this.capaValidador = capaValidador;
    }




    @GetMapping("/")
    public String prueba2 (){
        return "true";
    }

    @GetMapping("/prueba")
    public boolean prueba (){
        return true;
    }





    @PostMapping("archivo-validar")
    public boolean validarArchivo(@RequestBody String linea){

        return this.capaValidador.validarLinea(linea);

    }



}
