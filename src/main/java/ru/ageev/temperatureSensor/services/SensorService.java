package ru.ageev.temperatureSensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ageev.temperatureSensor.Dto.MeasurementDto;
import ru.ageev.temperatureSensor.models.Measurement;
import ru.ageev.temperatureSensor.repositories.SensorRepositories;
import ru.ageev.temperatureSensor.util.MeasurementErrorException;
import ru.ageev.temperatureSensor.util.MeasurementErrorResponse;
import ru.ageev.temperatureSensor.util.MeasurementValidator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private final SensorRepositories sensorRepositories;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;


    @Autowired
    public SensorService(SensorRepositories sensorRepositories, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.sensorRepositories = sensorRepositories;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    public List<MeasurementDto> findAll() {
        List<Measurement> measurements = sensorRepositories.findAll();

        return measurements.stream().map(
                measurement -> modelMapper.map(measurement, MeasurementDto.class)).collect(Collectors.toList());
    }

    public ResponseEntity<HttpStatus> save(MeasurementDto measurementDto, BindingResult bindingResult) throws MeasurementErrorException {
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

        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
        measurement.setTimestamp(new Timestamp(System.currentTimeMillis()));

        sensorRepositories.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public int getRainyDaysCount() {
        return sensorRepositories.countByIsRaining(true);
    }
}
