package ru.ageev.temperatureSensor.util.exceptions;

public class RegistrationErrorException extends RuntimeException {
    public RegistrationErrorException(String message) {
        super(message);
    }
}
