package com.api.microservi2;

import com.api.microservi2.CapRepository.CapaRepository;
import com.api.microservi2.Exception.LecturasDatosEx;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CapaServices {


    private CapaRepository capaRepository;
    private RestTemplate restTemplate = null;

    private int contadorLineaValida = 0;
    private int getContadorLineaInValida = 0;


    @Autowired
    public CapaServices(CapaRepository capaRepository) {

        this.capaRepository = new CapaRepository();
        this.restTemplate = restTemplate;
    }


    public String procesarArchivo(Archivo archivo) {

        if (archivo.getTipo().equals("csv")) {
            List<String> listaRegistros = listarArchivoCsv(archivo);
            RespuestaArchivo respuesta =procesarLineas(listaRegistros);


            return respuesta.Respuesta();

        } else if (archivo.getTipo().equals("excel")) {
            List<String> listaRegistros = listarArchivoCsv(archivo);

            System.out.println("es un archivo de excel ");

            RespuestaArchivo respuesta =procesarLineas(listaRegistros);

            return respuesta.Respuesta();
        } else {
            return "es un no es nada  " + archivo.getTipo();
        }
    }


    public List<String> listarArchivoCsv(Archivo archivo) {

        String delimiter = ",";
        List<String> Lineas = new ArrayList<>();

        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo.getRuta()));
            String linea;
            int contador=0;
            while ((linea = entrada.readLine()) != null ) {

                if(contador>0){
                    Lineas.add(linea);
                }
                contador++;
            }


            entrada.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
            throw new LecturasDatosEx("Excepcion al listar   ");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            throw new LecturasDatosEx("Excepcion al listar   ");
        }
        return Lineas;
    }


    public List<String> listarArchivoExcel(Archivo archivo) {
        List<String> lineasJson = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(archivo.getRuta()))) {
            Sheet sheet = workbook.getSheetAt(0);
            int contadorCampos = 0;

            for (Row row : sheet) {
                Map<String, String> datos = new HashMap<>();

                for (Cell cell : row) {
                    String cellValue = cell.getStringCellValue();
                    datos.put("campo" + cell.getColumnIndex(), cellValue);
                }

                // Convertir el objeto Map a una cadena de texto JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String datosJson = objectMapper.writeValueAsString(datos);

                // Agregar la cadena de texto JSON a la lista de líneas JSON
                lineasJson.add(datosJson);

                contadorCampos++;
            }




        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            throw new LecturasDatosEx("Excepcion al listar");
        }

        return lineasJson;
    }


    public RespuestaArchivo procesarLineas(List<String> lineas) {
        int contadorLineasValidas=0;
        int contadorLineasinvalidas=0;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        try {
            for (String lineaJson : lineas) {
                Boolean response = restTemplate.postForObject(
                        "http://localhost:8080/api/v1/archivo-validar",
                        new HttpEntity<>(lineaJson, headers),
                                Boolean.class);
                if(response ==true){
                    contadorLineasValidas++;
                }else{
                    contadorLineasinvalidas++;
                }
                // Procesar la respuesta
            }

            return new RespuestaArchivo(contadorLineasValidas,contadorLineasinvalidas );

        } catch (Exception e) {
            System.out.println(e);
        }
        return new RespuestaArchivo(contadorLineasValidas,contadorLineasinvalidas );
    }





}

