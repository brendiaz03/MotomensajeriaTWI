package com.tallerwebi.dominio.vehiculo;

public interface VehiculoServicio {

    Vehiculo registrarVehiculo(Vehiculo vehiculo);
    void actualizarVehiculo(Vehiculo vehiculo);

    Vehiculo buscarVehiculoPorPatente(String patente);
}
