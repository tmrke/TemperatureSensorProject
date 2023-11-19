package ru.ageev.temperatureSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ageev.temperatureSensor.models.Sensor;
import ru.ageev.temperatureSensor.repositories.SensorRepositories;

import java.util.Optional;

@Service
public class RegistrationValidService {
    private final SensorRepositories sensorRepositories;

    @Autowired
    public RegistrationValidService(SensorRepositories sensorRepositories) {
        this.sensorRepositories = sensorRepositories;
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepositories.findByName(name);
    }
}
