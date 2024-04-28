package com.tallerwebi.dominio.entidades.vehiculos;

import com.tallerwebi.dominio.dto.VehiculoDto;
import com.tallerwebi.dominio.enums.TipoVehiculo;

import javax.persistence.Entity;

@Entity
public class Auto extends Vehiculo {

    public Auto(VehiculoDto vehiculoDto) {
        super(vehiculoDto.getMarca(), vehiculoDto.getModelo(), vehiculoDto.getPatente(), vehiculoDto.getColor(), vehiculoDto.getPesoSoportado(), vehiculoDto.getDimensionDisponible(), TipoVehiculo.AUTO);
    }

    public Auto(Vehiculo vehiculo) {
        super(vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getPatente(), vehiculo.getColor(), vehiculo.getPesoSoportado(), vehiculo.getDimensionDisponible(), TipoVehiculo.AUTO);
    }

    public Auto() {

    }
}