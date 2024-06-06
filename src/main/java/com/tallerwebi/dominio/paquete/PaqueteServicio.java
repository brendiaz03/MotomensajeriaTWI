package com.tallerwebi.dominio.paquete;

public interface PaqueteServicio {
    Paquete guardarPaquete(Paquete paquete);

    void editarPaquete(Paquete paquete);

    Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException;
}
