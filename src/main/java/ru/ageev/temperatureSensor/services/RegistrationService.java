package ru.ageev.temperatureSensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.models.Sensor;
import ru.ageev.temperatureSensor.repositories.SensorRepositories;
import ru.ageev.temperatureSensor.util.exceptions.RegistrationErrorException;
import ru.ageev.temperatureSensor.util.validators.RegistrationValidator;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    private final SensorRepositories sensorRepositories;
    private final RegistrationValidator registrationValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationService(SensorRepositories sensorRepositories, RegistrationValidator registrationValidator, ModelMapper modelMapper) {
        this.sensorRepositories = sensorRepositories;
        this.registrationValidator = registrationValidator;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ResponseEntity<HttpStatus> registration(SensorDto sensorDto, BindingResult bindingResult) throws RegistrationErrorException {
        registrationValidator.validate(sensorDto, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessages
                        .append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new RegistrationErrorException(errorMessages.toString());
        }

        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);
        sensor.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

        sensorRepositories.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
