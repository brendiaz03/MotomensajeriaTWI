package com.tallerwebi.dominio.conductor;

public interface IRepositoryConductor {
    Boolean registrar(Conductor nuevoConductor);

    Conductor buscarConductor(Integer id);

    Conductor actualizarConductor(Conductor nuevoConductor);

}
