package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;

public interface ConductorRepository {

    Conductor buscarConductorPorEmail(String email);

    void actualizarInfoDelConductor(Conductor conductorActual);

    void guardarVehiculo(Vehiculo vehiculo);
}
