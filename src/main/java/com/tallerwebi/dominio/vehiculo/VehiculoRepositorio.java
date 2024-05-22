package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;

public interface VehiculoRepositorio {

    Vehiculo buscarVehiculoPorPatente(String patente);
    Vehiculo guardarVehiculo(Vehiculo vehiculo);
    void editar(Vehiculo vehiculo);

}
