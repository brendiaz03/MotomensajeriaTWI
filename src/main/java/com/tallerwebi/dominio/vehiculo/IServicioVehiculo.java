package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.enums.TipoVehiculo;

import java.util.List;

public interface IServicioVehiculo {

    List<Vehiculo> get();

    List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo);

}
