package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeService {
    List<Viaje> obtenerViajes();

    Viaje obtenerViajePorIdPaquete(Integer id);

    Viaje obtenerViajePorId(Integer id);

    List<Viaje> obtenerViajesPorIdCliente(Integer idCliente);

    List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idCliente);
}