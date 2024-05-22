package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;

import java.util.List;

public interface ViajeServicio {

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Viaje obtenerViajeAceptadoPorId(Integer id);

    List<Viaje> obtenerHistorialDeViajes(Conductor conductor);

    Viaje actualizarViajeConConductor(Viaje viaje);

//    List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor);
//
//    Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor);
}