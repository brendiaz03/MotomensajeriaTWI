package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeRepository {

    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Boolean actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor);
}
