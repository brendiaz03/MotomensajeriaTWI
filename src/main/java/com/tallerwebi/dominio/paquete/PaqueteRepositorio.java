package com.tallerwebi.dominio.paquete;

public interface PaqueteRepositorio {
    Paquete guardarPaquete(Paquete paquete);
    void editarPaquete(Paquete paquete);
    Paquete obtenerPaquetePorId(Integer paqueteId);
}
