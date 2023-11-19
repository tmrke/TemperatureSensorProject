package ru.ageev.temperatureSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.services.RegistrationService;

@Controller
@RequestMapping("sensors")
public class SensorControllers {
    private final RegistrationService registrationService;

    @Autowired
    public SensorControllers(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(
            @RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult) {
        return registrationService.registration(sensorDto, bindingResult);
    }
}
