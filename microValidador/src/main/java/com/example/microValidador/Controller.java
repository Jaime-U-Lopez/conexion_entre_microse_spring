package com.example.microValidador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class Controller {


    private CapaValidador capaValidador;

    @Autowired
    public Controller(CapaValidador capaValidador) {
        this.capaValidador = capaValidador;
    }


    @GetMapping("/")
    public String prueba2() {
        return "true";
    }


    @PostMapping("archivo/{tipo}")
    public boolean validarArchivo(@RequestBody String linea, @PathVariable("tipo") String tipo) {

        return this.capaValidador.validarArchivo(linea, tipo);

    }


}
