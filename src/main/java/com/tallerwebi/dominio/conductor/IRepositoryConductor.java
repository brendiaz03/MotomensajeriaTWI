package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.conductor.Conductor;

public interface IRepositoryConductor {
    void registrar(Conductor nuevoConductor);

    Conductor buscarConductor(Integer dni);

    Conductor actualizarConductor(Conductor nuevoConductor);

}
