package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Datos.DatosViaje;

import java.util.List;

public interface ViajeRepository {
    void guardarViaje(Viaje viaje);

    List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos();

    Viaje ObtenerViajePorIdPaquete(Integer idPaquete);

    Viaje obtenerViajePorId(Integer id);

    List<Viaje> obtenerLosViajesDeUnCliente(Integer idCliente);

    List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idCliente);
}
