package com.tallerwebi.dominio.vehiculo;

public interface VehiculoRepositorio {

    void registrarVehiculo(Vehiculo vehiculo);
    Vehiculo buscarVehiculoPorPatente(String patente);

}
