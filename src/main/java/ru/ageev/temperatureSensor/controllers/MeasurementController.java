package ru.ageev.temperatureSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import ru.ageev.temperatureSensor.Dto.MeasurementDto;
import ru.ageev.temperatureSensor.services.MeasurementService;
import ru.ageev.temperatureSensor.services.SensorDetailsService;

import java.util.List;

@RestController
@RequestMapping("measurement")
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorDetailsService sensorDetailsService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(
            @RequestBody @Valid MeasurementDto measurementDto,
            BindingResult bindingResult) {

        return measurementService.save(measurementDto, bindingResult);
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @GetMapping
    public List<MeasurementDto> getMeasurements() {
        return measurementService.findAll();
    }
}
