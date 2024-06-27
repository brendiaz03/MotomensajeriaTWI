package com.tallerwebi.dominio.paquete;

public interface PaqueteRepositorio {
    Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException;
    Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException;
}
