package com.example.microValidador;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CapaValidador {

/*

I save informatin Micro"
 */
    public boolean validarArchivo(String lineaArchivo, String tipo) {

        Boolean respuesta = false;
        String tipoSwitch = tipo;
        switch (tipoSwitch) {
            case "csv":
                respuesta = archivoCsv(lineaArchivo);
                break;
            case "excel":
                respuesta = archivoExcel(lineaArchivo);
                break;
            default:
                return respuesta;
        }

        return respuesta;


    }


    public Boolean archivoCsv(String lineaArchivo) {

        String[] ArrayJobTitle = {"Haematologist", "Phytotherapist", "Building surveyor", "Insurance",
                "account manager", "Educational psychologist"};
        boolean correoValido = false;
        boolean fechaValida = false;
        boolean jobTitle = false;
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

        String item2 = values[8];
        boolean JobEnable = Arrays.stream(ArrayJobTitle).anyMatch(item -> item.contains(item2));

        if (JobEnable) {
            jobTitle = true;
        }


        return correoValido &&fechaValida &&jobTitle;
}


    public Boolean archivoExcel(String lineaArchivo) {
        String[] ArrayReportType = {"Near Miss", "Lost Time", "First Aid"};
        Boolean injuryLocation = false;
        Boolean reportType = false;
        String[] filas = lineaArchivo.split(",");

        if (filas[1] != "N/A") {
            injuryLocation = true;
        }

        String celda= filas[7];
        boolean reportTypeEnable = Arrays.stream(ArrayReportType)
                .anyMatch(item -> item.contains(celda));

            if (reportTypeEnable) {
                reportType = true;
            }


        return injuryLocation && reportType;
    }

}








