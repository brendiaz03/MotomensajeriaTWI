package com.tallerwebi.dominio.paquete;

import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;

public interface PaqueteServicio {
    Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException;
    Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException;
}
