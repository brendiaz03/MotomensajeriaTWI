package com.tallerwebi.dominio.conductor;


public interface ConductorRepositorio {

    Conductor registrar(Conductor nuevoConductor);
    Conductor buscarConductor(Integer id);
    Boolean editarConductor(Conductor nuevoConductor);
    Conductor buscarDuplicados(String email, String nombreUsuario);
    void borrarConductor(Conductor conductor);
}
