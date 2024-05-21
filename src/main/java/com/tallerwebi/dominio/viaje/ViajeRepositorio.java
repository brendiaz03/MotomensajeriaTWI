package com.tallerwebi.dominio.viaje;

import java.util.List;

public interface ViajeRepositorio {

    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Viaje actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor);

    List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor);

    Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor);
}