package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeService {
    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idCliente);

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    String actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor);
}