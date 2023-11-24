package ru.ageev.temperatureSensor.Dto;

import jakarta.validation.constraints.*;

public class MeasurementDto {
    @NotNull(message = "isRaining must be true or false")
    private boolean isRaining;

    @Min(value = -60, message = "value must be greater than or equal to -60")
    @Max(value = 60, message = "value must be less than or equal to 60")
    private double value;

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public boolean getIsRaining() {
        return isRaining;
    }

    public void setIsRaining(boolean isRaining) {
        this.isRaining = isRaining;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
