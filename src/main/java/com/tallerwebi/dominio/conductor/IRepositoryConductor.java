package com.tallerwebi.dominio.conductor;

public interface IRepositoryConductor {
    void registrar(Conductor nuevoConductor);

    Conductor buscarConductor(Integer dni);

    Conductor actualizarConductor(Conductor nuevoConductor);

}
