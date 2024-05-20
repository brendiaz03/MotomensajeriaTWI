package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;

public interface VehiculoServicio {

    Boolean registrarVehiculoSiPatenteNoEstaYaCargada(Vehiculo vehiculo);
    Vehiculo getVehiculoByIdConductor(Conductor conductor);
    void EditarVehiculo(Vehiculo vehiculo);
}
