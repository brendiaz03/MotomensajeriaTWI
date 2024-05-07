package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServicioVehiculo {

    List<Vehiculo> get();

    List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo);

    List<Vehiculo> setVehiculos();
}
