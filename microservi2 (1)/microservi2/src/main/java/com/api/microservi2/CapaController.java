package com.api.microservi2;


import com.api.microservi2.CapaServices;
import com.api.microservi2.RespuestaArchivo;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CapaController {

    private CapaServices capaServices;

    public CapaController(CapaServices capaServices ) {
        this.capaServices = capaServices;
    }

    @PostMapping("archivo-validacion")
    public String Procesar( @RequestBody Archivo archivo){
        return this.capaServices.procesarArchivo(archivo);

    }






    @PostMapping("pruebaArchivo")
    public String Procesar2( @RequestBody Archivo archivo){
        return  archivo.getRuta();

    }





    @GetMapping("/prueba")
    public boolean prueba2() {
        return true;
    }


    private boolean enviarArchivo(MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = "http://servidor-validacion.com/api/validar";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    }


    private boolean isValidEmail(String email) {
        // Validar el correo electrónico usando una expresión regular
        // Retorna true si el correo es válido, false si no lo es.
        return true;
    }

}


