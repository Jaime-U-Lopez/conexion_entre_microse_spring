package com.api.microservi2;

import com.api.microservi2.CapRepository.CapaRepository;
import com.api.microservi2.Exception.LecturasDatosEx;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

            List<String> listaRegistros = generarLineasArchivo(archivo);
            RespuestaArchivo respuesta = procesarLineas(listaRegistros , archivo);
            return respuesta.Respuesta();

    }


    public List<String> generarLineasArchivo(Archivo archivo) {
        List<String> listaVacia = new ArrayList<>();
        String tipo = archivo.getTipo();
        switch (tipo) {
            case "csv":
                listaVacia = archivoCsv(archivo);
                break;
            case "excel":
                listaVacia = archivoExcel(archivo);
                break;
            default:
                return listaVacia;
        }

        return listaVacia;


    }

    public List<String> archivoCsv(Archivo archivo) {


        List<String> Lineas = new ArrayList<>();

        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo.getRuta()));
            String linea;
            int contador = 0;
            while ((linea = entrada.readLine()) != null) {

                if (contador > 0) {
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




    public  List<String> archivoExcel(Archivo archivo) {
        List<String> lines = new ArrayList<>();


        try (FileInputStream inputStream = new FileInputStream(new File(archivo.getRuta()));
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet("SafetyData");
            Iterator<Row> rowIterator = sheet.rowIterator();
            int contador =0;
            while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    StringBuilder line = new StringBuilder();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        line.append(cell.toString());
                        line.append(",");
                    }
                if(contador>0){
                    lines.add(line.toString());
                }
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public RespuestaArchivo procesarLineas(List<String> lineas, Archivo archivo) {
        int contadorLineasValidas = 0;
        int contadorLineasinvalidas = 0;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        try {
            for (String lineaJson : lineas) {
                Boolean response = restTemplate.postForObject("http://localhost:8080/api/v1/archivo/"+ archivo.getTipo() , new HttpEntity<>(lineaJson, headers), Boolean.class);
                if (response == true) {
                    contadorLineasValidas++;
                } else {
                    contadorLineasinvalidas++;
                }
                // Procesar la respuesta
            }

            return new RespuestaArchivo(archivo,contadorLineasValidas, contadorLineasinvalidas );

        } catch (Exception e) {
            System.out.println(e);
        }
        return new RespuestaArchivo(archivo, contadorLineasValidas, contadorLineasinvalidas );
    }


}

