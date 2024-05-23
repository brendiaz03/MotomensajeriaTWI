package com.tallerwebi.dominio.viaje;

import com.tallerwebi.dominio.conductor.Conductor;
import org.springframework.beans.MutablePropertyValues;

import java.util.List;

public interface ViajeRepositorio {

    List<Viaje> obtenerViajesPorConductor(Conductor conductor);

    List<Viaje> obtenerLasSolicitudesDeViajesPendientes();

    void editar(Viaje viaje);

    Viaje obtenerViajePorId(Integer id);
}