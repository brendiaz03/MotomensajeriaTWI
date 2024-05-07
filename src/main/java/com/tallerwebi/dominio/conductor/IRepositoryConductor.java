package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.conductor.Conductor;

public interface IRepositoryConductor {
    Boolean registrar(Conductor nuevoConductor);

    Conductor buscarConductor(Integer id);

    Conductor actualizarConductor(Conductor nuevoConductor);

}
