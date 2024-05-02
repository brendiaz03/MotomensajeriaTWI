package com.tallerwebi.dominio;

public interface ClienteService {
    Paquete buscarPaquete(Integer idPaquete);

    Double calcularEnvio(String domicilioSalida, String domicilioEntrega);

    void guardarPaquete(Paquete paqueteActual);
}