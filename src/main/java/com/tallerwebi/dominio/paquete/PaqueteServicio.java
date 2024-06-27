package com.tallerwebi.dominio.paquete;

public interface PaqueteServicio {
    Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException;
    Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException;
}
