package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;

public interface VehiculoServicio {

    Vehiculo registrarVehiculo(Vehiculo vehiculo) throws VehiculoDuplicadoException;
    void actualizarVehiculo(Vehiculo vehiculo);
}
