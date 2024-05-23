package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;

import javax.transaction.Transactional;

public interface VehiculoRepositorio {

    Vehiculo buscarVehiculoPorPatente(String patente);
    Vehiculo guardarVehiculo(Vehiculo vehiculo);
    void editar(Vehiculo vehiculo);

    void save(Vehiculo vehiculoEsperado);

    @Transactional
    void update(Vehiculo vehiculo);
}
