package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;

import java.util.List;

public interface ViajeRepositorio {

    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<Viaje> obtenerViajesPorConductor(Conductor conductor);

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    void actualizarViaje(Viaje viaje);

    Viaje obtenerViajePorId(Integer id);

//    List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor);
//
//    Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor);
}