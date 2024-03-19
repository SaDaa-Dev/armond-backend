package com.chandev.armond;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArmondApplication {



	public static void main(String[] args) {
		SpringApplication.run(ArmondApplication.class, args);
	}
}
