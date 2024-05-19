package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeRepository {

    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idCliente);

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Boolean actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor);
}
