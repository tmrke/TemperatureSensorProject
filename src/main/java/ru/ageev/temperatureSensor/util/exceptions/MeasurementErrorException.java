package ru.ageev.temperatureSensor.util.exceptions;

public class MeasurementErrorException extends RuntimeException {
    public MeasurementErrorException(String message) {
        super(message);
    }
}
