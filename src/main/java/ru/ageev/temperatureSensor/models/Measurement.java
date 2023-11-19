package ru.ageev.temperatureSensor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "temperature_measurements")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_raining")
    @NotNull(message = "isRaining must be true or false")
    private boolean isRaining;

    @Column(name = "value")
    @NotNull(message = "value can't be empty")
    @Min(value = -60, message = "value must be greater than or equal to -60")
    @Max(value = 60, message = "value must be less than or equal to 60")
    private double value;


    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "sensor_id")
    private int sensorId;

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
