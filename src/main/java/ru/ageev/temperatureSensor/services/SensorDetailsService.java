package ru.ageev.temperatureSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ageev.temperatureSensor.models.Sensor;
import ru.ageev.temperatureSensor.repositories.SensorRepositories;
import ru.ageev.temperatureSensor.security.SensorDetails;

import java.util.Optional;

@Service
public class SensorDetailsService implements UserDetailsService {
    private final SensorRepositories sensorRepositories;

    @Autowired
    public SensorDetailsService(SensorRepositories sensorRepositories) {
        this.sensorRepositories = sensorRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Sensor> sensor = sensorRepositories.findByName(username);

        if (sensor.isEmpty()) {
            throw new UsernameNotFoundException("Sensor with " + username + " name not found");
        }

        return new SensorDetails(sensor.get());
    }
}
