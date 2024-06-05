package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.presentacion.Datos.DatosViaje;

import java.util.List;

public interface ViajeServicio {

    DatosViaje obtenerViajeAceptadoPorId(Integer id);

    List<DatosViaje> obtenerHistorialDeViajes(Conductor conductor) throws UsuarioNoEncontradoException;

    Viaje actualizarViaje(Viaje viaje);

    List<Viaje> obtenerViajesEnProceso(Conductor conductor);

    List<DatosViaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar);

    void descartarViaje(Integer idViaje, Conductor conductor);

    Boolean estaPenalizado(Conductor conductor);

    void aceptarViaje(DatosViaje viaje, Conductor conductor);

    List<Viaje> calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(List<Viaje> viajes);

    void cancelarViaje(DatosViaje viaje);

    void terminarViaje(DatosViaje viaje);

    DatosViaje mapearViajeADatosViajeHistorial(Viaje viaje);

    DatosViaje mapearViajeADatosViaje(Viaje viaje);

    void crearViaje(Cliente cliente, Viaje viaje, Paquete paquete);
     Viaje buscarViaje(Integer idViaje);
}