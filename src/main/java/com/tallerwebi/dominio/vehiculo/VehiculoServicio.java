package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;

public interface VehiculoServicio {

    Vehiculo registrarVehiculo(Vehiculo vehiculo);
    void actualizarVehiculo(Vehiculo vehiculo);
}
