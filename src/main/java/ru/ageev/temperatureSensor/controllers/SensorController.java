package ru.ageev.temperatureSensor.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.services.SensorService;

import java.net.http.HttpResponse;

@Controller
@RequestMapping("sensors")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(
            @RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult, HttpServletRequest request) {
        return sensorService.registration(sensorDto, bindingResult, request);
    }
}
