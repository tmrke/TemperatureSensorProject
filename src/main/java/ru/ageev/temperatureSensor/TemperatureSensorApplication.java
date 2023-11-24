package ru.ageev.temperatureSensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TemperatureSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureSensorApplication.class, args);
		LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel("org.springframework.security", LogLevel.DEBUG);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
