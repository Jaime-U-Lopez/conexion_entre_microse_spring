package com.api.microservi2.CapRepository;


import com.api.microservi2.Exception.LecturasDatosEx;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CapaRepository {



    public Boolean existe(String nombreRecurso)  {
        File archivo = new File(nombreRecurso);
        return archivo.exists();
    }

    public List<String> listarArchivoCsv(String nombreArchivo)  {

        File archivo = new File(nombreArchivo);
        String delimiter = ",";
        List<String> Lineas = new ArrayList<>();

        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo));
            String linea = null;
            linea = entrada.readLine();
            while (linea != null) {
             String [] values = linea.split(delimiter);
             String nuevoString= values.toString();
                Lineas.add(nuevoString);
            }

            entrada.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
            throw new LecturasDatosEx("Excepcion al listar peliculas  ");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            throw new LecturasDatosEx("Excepcion al listar peliculas  ");
        }
        return Lineas;
    }






}



