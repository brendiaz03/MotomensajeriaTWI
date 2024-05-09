package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IRepositoryVehiculo {

    List<Vehiculo> get();
    List<Vehiculo> getByTipoVehiculo(TipoVehiculo tipoVehiculo);

    List<Vehiculo> obtenerTodosLosVehiculos();

    Vehiculo obtenerVehiculoPorElIdDelConductor(Integer numeroDeDni);

    List<Vehiculo> obtenerTodosLosVehiculosDelConductor(Integer id);
}
