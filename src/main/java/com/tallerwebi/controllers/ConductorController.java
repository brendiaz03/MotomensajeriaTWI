package com.tallerwebi.controllers;

import com.tallerwebi.dto.ConductorDto;
import com.tallerwebi.dto.ConductorYVehiculoDto;
import com.tallerwebi.dto.VehiculoDto;
import com.tallerwebi.models.*;
import com.tallerwebi.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ConductorController {

    private ConductorService conductorService;

    @Autowired
    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @PostMapping ("/nuevoConductor")
    public String nuevoConductor(@RequestBody ConductorYVehiculoDto conductorYVehiculoDto) {

        Conductor nuevoConductor = crearConductor(conductorYVehiculoDto.getConductorDto());

        Vehiculo nuevoVehiculo = crearVehiculo(conductorYVehiculoDto.getVehiculoDto());

        Conductor nuevoConductorConSuVehiculo = crearConductorConVehiculo(nuevoConductor, nuevoVehiculo);

        conductorService.guardarConductorYVehiculo(nuevoConductorConSuVehiculo);

        return "Conductor creado exitosamente";
    }

    private Conductor crearConductorConVehiculo(Conductor conductor, Vehiculo vehiculo) {
        return new Conductor(conductor, vehiculo);
    }

    private Conductor crearConductor(ConductorDto conductorDto) {
        return new Conductor(conductorDto);
    }

    private Vehiculo crearVehiculo(VehiculoDto vehiculoDto) {
        switch (vehiculoDto.getTipoVehiculo()) {
            case AUTO:
                return new Auto(vehiculoDto);
            case MOTO:
                return new Moto(vehiculoDto);
            case CAMION:
                return new Camion(vehiculoDto);
            default:
                throw new IllegalArgumentException("Tipo de vehículo no válido: ");
        }
    }

    @GetMapping("/obtenerConductores")
    public List<Conductor> obtenerConductores() {
        return this.conductorService.obtenerConductores();
    }
}