package com.tallerwebi.services;

import com.tallerwebi.models.Conductor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConductorService {
    void guardarConductorYVehiculo(Conductor conductor);

    List<Conductor> obtenerConductores();
}