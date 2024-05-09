package com.omair;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OmairApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmairApplication.class, args);
	}
    @Bean
	public ModelMapper modle(){
		return new ModelMapper();
	}
}
