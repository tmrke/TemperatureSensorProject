package ru.ageev.temperatureSensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.ageev.temperatureSensor.Dto.MeasurementDto;
import ru.ageev.temperatureSensor.models.Measurement;
import ru.ageev.temperatureSensor.repositories.MeasurementRepositories;
import ru.ageev.temperatureSensor.util.exceptions.MeasurementErrorException;
import ru.ageev.temperatureSensor.util.validators.MeasurementValidator;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    private final MeasurementRepositories measurementRepositories;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;
    private final SensorService sensorService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public MeasurementService(MeasurementRepositories measurementRepositories, ModelMapper modelMapper, MeasurementValidator measurementValidator, SensorService sensorService, AuthenticationManager authenticationManager) {
        this.measurementRepositories = measurementRepositories;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
        this.sensorService = sensorService;
        this.authenticationManager = authenticationManager;
    }

    public List<MeasurementDto> findAll() {
        List<Measurement> measurements = measurementRepositories.findAll();

        return measurements.stream().map(
                measurement -> modelMapper.map(measurement, MeasurementDto.class)).collect(Collectors.toList());
    }

    public ResponseEntity<HttpStatus> save(
            MeasurementDto measurementDto,
            BindingResult bindingResult) throws MeasurementErrorException {
        measurementValidator.validate(measurementDto, bindingResult);

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

            throw new MeasurementErrorException(errorMessages.toString());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString()));
        } catch (BadCredentialsException e) {
            System.out.println("Че то не так");

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
        measurement.setTimestamp(new Timestamp(System.currentTimeMillis()));
        measurement.setOwner(sensorService.getSensorByName(authentication.getName()));

        measurementRepositories.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public int getRainyDaysCount() {
        return measurementRepositories.countByIsRaining(true);
    }
}
