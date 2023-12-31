package ru.ageev.temperatureSensor.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ageev.temperatureSensor.Dto.MeasurementDto;

@Component
public class MeasurementValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasFieldErrors("value")) {
            errors.rejectValue("value", "value incorrect");
        }
    }
}
