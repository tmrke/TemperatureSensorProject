package ru.ageev.temperatureSensor.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ageev.temperatureSensor.Dto.SensorDto;
import ru.ageev.temperatureSensor.services.RegistrationValidService;
import ru.ageev.temperatureSensor.util.exceptions.RegistrationErrorException;

@Component
public class RegistrationValidator implements Validator {
    private final RegistrationValidService registrationValidService;

    @Autowired
    public RegistrationValidator(RegistrationValidService registrationValidService) {
        this.registrationValidService = registrationValidService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) throws RegistrationErrorException {
        SensorDto sensorDto = (SensorDto) target;

        boolean sensorIsPresent = registrationValidService.findByName(sensorDto.getName()).isPresent();

        if (sensorIsPresent) {
            throw new RegistrationErrorException("Sensor with that name already exist");
        }

        if (sensorDto.getName().length() < 3 || sensorDto.getName().length() > 30) {
            errors.rejectValue("name", "name.length", "Name length must be between 3 and 30 characters");
        }

        if (sensorDto.getPassword().length() < 3 || sensorDto.getPassword().length() > 99) {
            errors.rejectValue("password", "password.length", "Password length must be between 3 and 99 characters");
        }
    }
}
