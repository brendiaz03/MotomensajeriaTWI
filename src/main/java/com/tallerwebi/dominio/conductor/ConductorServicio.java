package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;

import java.util.List;

public interface ConductorServicio {

    Conductor obtenerConductorPorId(Integer id) throws UsuarioNoEncontradoException;

    Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws UsuarioNoEncontradoException;

    Boolean estaPenalizado(Conductor conductor, List<Viaje> cantidadDescartados);

    void editarConductor(Conductor conductor);

    void despenalizarConductor(Conductor conductor);
}
