package com.tallerwebi.dominio.conductor;

import java.util.List;

public interface IServiceConductor {
    Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws Exception;

    Conductor obtenerConductorPorId(Integer id);
}
