package com.tallerwebi.dominio;

public interface ClienteRepository {
    Paquete buscarPaquete(Integer idPaquete);

    void guardarPaquete(Paquete paqueteActual);
}