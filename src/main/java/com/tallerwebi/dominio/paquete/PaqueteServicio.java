package com.tallerwebi.dominio.paquete;

public interface PaqueteServicio {
    Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException;

    void editarPaquete(Paquete paquete);

    Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException;
}
