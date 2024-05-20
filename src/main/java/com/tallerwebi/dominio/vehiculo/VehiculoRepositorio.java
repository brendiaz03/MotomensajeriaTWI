package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;

public interface VehiculoRepositorio {

    void registrarVehiculo(Vehiculo vehiculo);
    Vehiculo buscarVehiculoPorPatente(String patente);
    Vehiculo buscarVehiculoPorIdConductor(Conductor conductor);
    void actualizarVehiculo(Vehiculo vehiculo);
}
