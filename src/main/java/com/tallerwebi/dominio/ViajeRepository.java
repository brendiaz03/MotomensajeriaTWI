package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Conductor;

public interface ViajeRepository {
    Conductor buscarConductor(Integer idConductor);
}
