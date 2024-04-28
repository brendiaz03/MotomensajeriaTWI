package com.tallerwebi.dominio.entidades.vehiculos;

import com.tallerwebi.dominio.dto.VehiculoDto;
import com.tallerwebi.dominio.enums.TipoVehiculo;

import javax.persistence.Entity;

@Entity
public class Camion extends Vehiculo {

    public Camion(VehiculoDto vehiculoDto) {
        super(vehiculoDto.getMarca(), vehiculoDto.getModelo(), vehiculoDto.getPatente(), vehiculoDto.getColor(), vehiculoDto.getPesoSoportado(), vehiculoDto.getDimensionDisponible(), TipoVehiculo.CAMION);
    }

    public Camion(Vehiculo vehiculo) {
        super(vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getPatente(), vehiculo.getColor(), vehiculo.getPesoSoportado(), vehiculo.getDimensionDisponible(), TipoVehiculo.CAMION);
    }

    public Camion() {

    }
}