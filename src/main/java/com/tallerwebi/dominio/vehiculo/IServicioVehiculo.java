package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.stereotype.Repository;
import com.tallerwebi.dominio.conductor.Conductor;

import java.util.List;

@Repository
public interface IServicioVehiculo {

    List<Vehiculo> get();

   List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo);

    List<Vehiculo> setVehiculos();

    Boolean queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(List<Vehiculo> vehiculos, Vehiculo vehiculo, Conductor conductor, List<Conductor>conductores);

    Boolean modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculo, Conductor conductor, List<Conductor>conductores);

    Boolean queExistaElConductorEnLaListaDeConductores(Conductor conductor, List<Conductor>conductores);
}
