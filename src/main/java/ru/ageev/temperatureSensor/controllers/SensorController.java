package ru.ageev.temperatureSensor.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.services.SensorService;

@Controller
@RestController
@RequestMapping("sensors")
public class SensorController {
    private final SensorService sensorService;
//    private final AuthenticationManager authenticationManager;

    @Autowired
    public SensorController(SensorService sensorService, AuthenticationManager authenticationManager) {
        this.sensorService = sensorService;
//        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<SensorDto> registration(
            @RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult) {
        ResponseEntity<SensorDto> response = sensorService.registration(sensorDto, bindingResult);
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sensorDto.getName(), sensorDto.getPassword()));

        return response;
    }
}
