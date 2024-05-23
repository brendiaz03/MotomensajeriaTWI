package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;

import java.util.List;

public interface ViajeServicio {

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    Viaje obtenerViajeAceptadoPorId(Integer id);

    List<Viaje> obtenerHistorialDeViajes(Conductor conductor);

    Viaje actualizarViaje(Viaje viaje);

    List<Viaje> obtenerViajesEnProceso(Conductor conductor);

}