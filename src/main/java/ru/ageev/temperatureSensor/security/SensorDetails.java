package ru.ageev.temperatureSensor.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ageev.temperatureSensor.models.Sensor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SensorDetails implements UserDetails {
    private final Sensor sensor;

    public SensorDetails(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return sensor.getPassword();
    }

    @Override
    public String getUsername() {
        return sensor.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Sensor getSensor() {
        return sensor;
    }

}
