package com.tallerwebi.dominio.entidades.vehiculos;

import com.tallerwebi.dominio.enums.TipoVehiculo;

import javax.persistence.Entity;

@Entity
public class Camion extends Vehiculo {

    public Camion(Vehiculo vehiculo) {
        super(vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getPatente(), vehiculo.getColor(), vehiculo.getPesoSoportado(), vehiculo.getDimensionDisponible(), TipoVehiculo.CAMION);
    }

    public Camion() {

    }
}