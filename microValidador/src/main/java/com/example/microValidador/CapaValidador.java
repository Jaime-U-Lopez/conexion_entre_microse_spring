package com.example.microValidador;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapaValidador {

/*
    public boolean validadorArchivo(String lineaArchivo) {

        List<String> contadorLineas = new ArrayList<>();

        int contadorTrue = 0;
        int contadorFalse = 0;

        String delimiter = ",";
        while (true) {
            String[] values = lineaArchivo.split(delimiter);
            if (values != null) {
                String Correo = values[5];
                String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
                boolean respuesta = Correo.matches(EMAIL_REGEX);

                if (respuesta) {
                    contadorTrue++;
                    String respuetasTrue = "True : " + contadorTrue;
                    contadorLineas.add(respuetasTrue);

                } else {
                    contadorFalse++;
                    String respuestasFalse = "False : " + contadorFalse;
                    contadorLineas.add(respuestasFalse);
                }

            }

            break;

        }

        return contadorLineas;
    } */

    public boolean validarLinea(String lineaArchivo) {

        boolean correoValido = false;
        boolean fechaValida = false;

        String[] values = lineaArchivo.split(",");

        if (values != null) {
            String Correo = values[5];
            String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
            correoValido = Correo.matches(EMAIL_REGEX);

            String fFechaNacimiento = values[7];
            String[] valuesFecha = fFechaNacimiento.split("-");
            int anio = Integer.parseInt(valuesFecha[0]);
            if (anio > 1980) {
                fechaValida = true;
            }
        }

        return correoValido && fechaValida;
    }

}








