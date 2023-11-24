package ru.ageev.temperatureSensor.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.models.Sensor;
import ru.ageev.temperatureSensor.repositories.SensorRepositories;
import ru.ageev.temperatureSensor.util.JwtTokenUtil;
import ru.ageev.temperatureSensor.util.exceptions.RegistrationErrorException;
import ru.ageev.temperatureSensor.util.responses.EntityResponse;
import ru.ageev.temperatureSensor.util.validators.RegistrationValidator;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SensorService {
    private final SensorRepositories sensorRepositories;
    private final RegistrationValidator registrationValidator;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final SensorDetailsService sensorDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SensorService(
            SensorRepositories sensorRepositories,
            RegistrationValidator registrationValidator,
            ModelMapper modelMapper, JwtTokenUtil jwtTokenUtil,
            SensorDetailsService sensorDetailsService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sensorRepositories = sensorRepositories;
        this.registrationValidator = registrationValidator;
        this.modelMapper = modelMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.sensorDetailsService = sensorDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public ResponseEntity<EntityResponse> registration(SensorDto sensorDto,
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
        sensor.setPassword(bCryptPasswordEncoder.encode(sensor.getPassword()));

        sensorRepositories.save(sensor);

        UserDetails sensorDetails = sensorDetailsService.loadUserByUsername(sensor.getName());
        String token = jwtTokenUtil.generateToken(sensorDetails);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(sensorDetails, null, sensorDetails.getAuthorities())
        );

        return ResponseEntity.ok(new EntityResponse(token));
    }

    public Sensor getSensorByName(String name) {
        return sensorRepositories.findByName(name).orElse(null);
    }
}
