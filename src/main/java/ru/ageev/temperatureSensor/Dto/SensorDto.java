package ru.ageev.temperatureSensor.Dto;

import jakarta.validation.constraints.*;

public class SensorDto {
       @NotEmpty
    @Size(min = 3, max = 30, message = "name length must be more than 2 and less 31")
    private String name;

    @NotEmpty
    @Size(min = 3, max = 30, message = "name length must be more than 2 and less 100")
    private String password;

    public SensorDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
