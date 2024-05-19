package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeService {
    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Viaje actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor);

    List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor);
}