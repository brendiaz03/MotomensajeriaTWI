package com.tallerwebi.dominio.conductor;

public interface IServiceConductor {
    Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws Exception;

    Conductor obtenerConductorPorId(Integer id);
}
