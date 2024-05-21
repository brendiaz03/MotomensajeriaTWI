package com.tallerwebi.dominio.conductor;


import com.tallerwebi.dominio.vehiculo.Vehiculo;

public interface ConductorRepositorio {

    Conductor registrar(Conductor nuevoConductor);
    Conductor buscarConductor(Integer id);
    Boolean editarConductor(Conductor nuevoConductor);
    Conductor buscarDuplicados(String email, String nombreUsuario);
    void borrarConductor(Conductor conductor);
    void agregarVehiculoAConductor(Integer conductorId, Vehiculo vehiculo);
}
