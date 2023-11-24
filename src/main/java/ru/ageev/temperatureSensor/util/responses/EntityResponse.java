package ru.ageev.temperatureSensor.util.responses;

public class EntityResponse {
    private String token;

    public EntityResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
