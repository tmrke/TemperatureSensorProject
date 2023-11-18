package ru.ageev.temperatureSensor.util;

public class MeasurementErrorException extends RuntimeException {
    public MeasurementErrorException(String message) {
        super(message);
    }
}
