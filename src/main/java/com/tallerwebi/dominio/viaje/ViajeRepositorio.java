package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;

import java.util.List;

public interface ViajeRepositorio {

    List<Viaje> obtenerViajesPorConductor(Conductor conductor);

    void editar(Viaje viaje);

    Viaje obtenerViajePorId(Integer id);

    List<Viaje> encontrarViajesCercanos(Double latitudConductor, Double longitudConductor, Double distanciaAFiltar);

    List<Viaje> traerTodosLosViajesPendientes();

    Viaje guardarViaje(Viaje viaje);

    List<Viaje> obtenerViajesPorCliente(Integer idusuario);

    void guardarViajeDuplicado(Viaje viajeObtenido);

   List<Viaje> traerTodosLosViajesDescartadosPorConductor(Conductor idConductor);

    List<Viaje> traerTodosLosViajesCanceladosPorConductor(Conductor conductor);

    List<Viaje> traerTodosLosViajesDescartadosQueAfectanPenalizacionPorConductor(Conductor conductor);

    }