package ru.ageev.temperatureSensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.models.Sensor;
import ru.ageev.temperatureSensor.repositories.SensorRepositories;
import ru.ageev.temperatureSensor.util.JwtTokenUtil;
import ru.ageev.temperatureSensor.util.exceptions.RegistrationErrorException;
import ru.ageev.temperatureSensor.util.validators.RegistrationValidator;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SensorService {
    private final SensorRepositories sensorRepositories;
    private final RegistrationValidator registrationValidator;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SensorService(SensorRepositories sensorRepositories, RegistrationValidator registrationValidator, ModelMapper modelMapper, JwtTokenUtil jwtTokenUtil) {
        this.sensorRepositories = sensorRepositories;
        this.registrationValidator = registrationValidator;
        this.modelMapper = modelMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public ResponseEntity<SensorDto> registration(SensorDto sensorDto,
                                                   BindingResult bindingResult) throws RegistrationErrorException {
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
        String token = jwtTokenUtil.generateToken();
        sensor.setToken(token);

        sensorRepositories.save(sensor);

        SensorDto responseSensorDto = modelMapper.map(sensor, SensorDto.class);
        responseSensorDto.setToken(token);

        return ResponseEntity.ok(responseSensorDto);
    }
}
