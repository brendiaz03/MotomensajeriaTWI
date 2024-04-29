package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Conductor;

public interface ViajeService {
    void calcularDistancia(Integer posicionInicial, Integer posicionFinal);

    Conductor buscarConductor(Integer idConductor);
}
