package ru.ageev.temperatureSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ageev.temperatureSensor.models.Measurement;

import java.util.List;

public interface SensorRepositories extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAll();

    int countByIsRaining(boolean isRaining);

}
