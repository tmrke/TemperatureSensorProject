package ru.ageev.temperatureSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import ru.ageev.temperatureSensor.Dto.MeasurementDto;
import ru.ageev.temperatureSensor.services.MeasurementService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RestController
@RequestMapping("measurement")
public class MeasurementController {
    private final MeasurementService measurementService;


    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(
            @RequestBody @Valid MeasurementDto measurementDto,
            BindingResult bindingResult,
            Authentication authentication) {

        if(authentication == null){
            System.out.println("ololo");
        }

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
