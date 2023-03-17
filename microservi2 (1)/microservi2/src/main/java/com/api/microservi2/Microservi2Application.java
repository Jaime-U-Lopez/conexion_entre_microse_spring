package com.api.microservi2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Microservi2Application {
	public static void main(String[] args) {

		SpringApplication.run(Microservi2Application.class, args);



		//String csvFilePath = "static/people.csv";

		//controller.validacionArchivo(csvFilePath);

/*
		String csvFilePath = "static/people.csv";
		ClassPathResource resource = new ClassPathResource(csvFilePath);
		String absolutePath = resource.getFile().getAbsolutePath();

		try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
			// Lee el archivo CSV y procesa los datos según sea necesario
		} catch (IOException e) {
			e.printStackTrace();
		}

		*/


	}



}
