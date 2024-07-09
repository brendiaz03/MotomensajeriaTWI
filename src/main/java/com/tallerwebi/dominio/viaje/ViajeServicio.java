package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.*;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.presentacion.Datos.DatosViaje;

import java.util.List;

public interface ViajeServicio {

    DatosViaje obtenerViajeAceptadoPorId(Integer id) throws ViajeNoEncontradoException;

    List<DatosViaje> obtenerHistorialDeViajesConductor(Conductor conductor) throws UsuarioNoEncontradoException;

    List<Viaje> obtenerViajesEnProceso(Conductor conductor) throws UsuarioNoEncontradoException;

    List<DatosViaje> filtrarViajesPorDistanciaDelConductor(Double latitudConductor, Double longitudConductor, Double distanciaAFiltrar, Conductor conductor) throws UsuarioNoEncontradoException, CoordenadasNoEncontradasException;

    void aceptarViaje(DatosViaje viaje, Conductor conductor) throws UsuarioNoEncontradoException, ViajeNoEncontradoException;

    List<Viaje> calcularLaDistanciaDelViajeEntreLaSalidaYElDestino(List<Viaje> viajes);

    void cancelarViaje(Viaje viaje) throws ViajeNoEncontradoException;

    void terminarViaje(DatosViaje viaje) throws ViajeNoEncontradoException;

    Viaje crearViaje(Cliente cliente, Viaje viaje, Paquete paquete) throws ViajeNoEncontradoException, ClienteNoEncontradoException, PaqueteNoEncontradoException, PrecioInvalidoException;

     Viaje buscarViaje(Integer idViaje) throws ViajeNoEncontradoException;

    List<Viaje> obtenerViajesEnProcesoDelCliente(Integer idUsuario) throws ClienteNoEncontradoException;

    void cancelarEnvio(Viaje viaje) throws ViajeNoEncontradoException;

    List<Viaje> obtenerViajesCanceladosDelCliente(Integer id) throws ClienteNoEncontradoException;

    Viaje obtenerViajePorId(Integer idViaje) throws ViajeNoEncontradoException;

    void duplicarViajeCancelado(Viaje viajeObtenido) throws ViajeNoEncontradoException;

    void noDuplicarViaje(Viaje viajeObtenido) throws ViajeNoEncontradoException;

    void actualizarViajeCancelado(Viaje viajeObtenido) throws ViajeNoEncontradoException;

    List<Viaje> obtenerHistorialDeEnvios(Integer idCliente) throws ClienteNoEncontradoException;

    void duplicarViajeDescartado(Viaje viajeObtenido, Conductor conductor) throws ClienteNoEncontradoException, ViajeNoEncontradoException;

    Double calcularPrecio(Viaje viaje);

    void actualizarViaje(Viaje viaje) throws ViajeNoEncontradoException;

    List<Viaje> filtrarViajesDuplicados(List<Viaje> viajes, List<Viaje> viajesDescartados);

    List<Viaje> compararPesosYDimesionesDeViajes(List<Viaje> viajesAMostrar, Conductor conductor);
}