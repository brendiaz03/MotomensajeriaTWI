package com.tallerwebi.models;

import com.tallerwebi.dto.VehiculoDto;
import com.tallerwebi.resources.TipoVehiculo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Moto extends Vehiculo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Moto(VehiculoDto vehiculoDto) {
        super(vehiculoDto.getMarca(), vehiculoDto.getModelo(), vehiculoDto.getPatente(), vehiculoDto.getColor(), vehiculoDto.getPesoSoportado(), vehiculoDto.getDimensionDisponible(), TipoVehiculo.MOTO);
    }

    public Moto() {

    }
}