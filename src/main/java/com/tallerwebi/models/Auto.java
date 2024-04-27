package com.tallerwebi.models;

import com.tallerwebi.dto.VehiculoDto;
import com.tallerwebi.resources.TipoVehiculo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Auto extends Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Auto(VehiculoDto vehiculoDto) {
        super(vehiculoDto.getMarca(), vehiculoDto.getModelo(), vehiculoDto.getPatente(), vehiculoDto.getColor(), vehiculoDto.getPesoSoportado(), vehiculoDto.getDimensionDisponible(), TipoVehiculo.AUTO);
    }

    public Auto() {

    }

}