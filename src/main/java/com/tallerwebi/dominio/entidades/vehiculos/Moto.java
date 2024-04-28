package com.tallerwebi.dominio.entidades.vehiculos;

import com.tallerwebi.dominio.dto.VehiculoDto;
import com.tallerwebi.dominio.enums.TipoVehiculo;

import javax.persistence.Entity;

@Entity
public class Moto extends Vehiculo {

    public Moto(String marca, String modelo, String patente, String color, Double pesoSoportado, Double dimensionDisponible, TipoVehiculo tipoVehiculo) {
        super(marca, modelo, patente, color, pesoSoportado, dimensionDisponible, tipoVehiculo);
    }

    public Moto(Vehiculo vehiculo) {
        super(vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getPatente(), vehiculo.getColor(), vehiculo.getPesoSoportado(), vehiculo.getDimensionDisponible(), TipoVehiculo.MOTO);
    }

    public Moto() {

    }
}