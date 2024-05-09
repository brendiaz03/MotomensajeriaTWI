package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.conductor.Conductor;

import java.util.List;

public interface IServicioVehiculo {

    List<Vehiculo> get();

   List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo);

    List<Vehiculo> setVehiculos();

   // Boolean queSePuedaRegistrarUnNuevoVehiculoConElIdDelConductor(Vehiculo nuevoVehiculo, Conductor conductor);

    //Boolean modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculo, Conductor conductor, List<Conductor>conductores);

    Boolean queExistaElConductorEnLaListaDeConductores(Conductor conductor, List<Conductor>conductores);

    List<Vehiculo> obtenerTodosLosVehiculos();

    Vehiculo obtenerVehiculoPorIdDelConductor(Integer id);

    List<Vehiculo> obtenerTodosLosVehiculosDelConductor(Integer id);

    Boolean queSePuedaRegistrarUnNuevoVehiculoConElIdDelConductorServicio(Vehiculo vehiculo1, Conductor nuevoConductor);
}
