package com.tallerwebi.dominio.viaje;

import java.util.List;

public interface ViajeServicio {
    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Viaje actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor);

    List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor);

    Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor);
}