package ru.ageev.temperatureSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import ru.ageev.temperatureSensor.Dto.MeasurementDto;
import ru.ageev.temperatureSensor.services.SensorService;

import java.util.List;

@Controller
@RequestMapping("measurement")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(
            @RequestBody @Valid MeasurementDto measurementDto, BindingResult bindingResult) {

        return sensorService.save(measurementDto, bindingResult);
    }

    @GetMapping("/rainyDaysCount")
    @ResponseBody
    public int getRainyDaysCount() {
        return sensorService.getRainyDaysCount();
    }

    @GetMapping
    @ResponseBody
    public List<MeasurementDto> getMeasurements() {
        return sensorService.findAll();
    }
}
