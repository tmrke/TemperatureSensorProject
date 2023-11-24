package ru.ageev.temperatureSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.services.SensorService;
import ru.ageev.temperatureSensor.util.responses.EntityResponse;

@RestController
@RequestMapping("sensors")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<EntityResponse> registration(
            @RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult) {

        return sensorService.registration(sensorDto, bindingResult);
    }
}
