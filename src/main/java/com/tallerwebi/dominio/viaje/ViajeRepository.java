package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeRepository {

    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Viaje actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor);

    List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor);
}