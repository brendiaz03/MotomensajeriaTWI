package com.tallerwebi.dominio.paquete;

import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;

public interface PaqueteRepositorio {
    Paquete guardarPaquete(Paquete paquete);
    Paquete obtenerPaquetePorId(Integer paqueteId);
}
