package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ConductorServicio {

    Conductor registrarConductorNoDuplicado(Conductor nuevoConductor) throws ConductorDuplicadoException;

    Conductor obtenerConductorPorId(Integer id) throws ConductorNoEncontradoException;

    void editarConductor(Conductor nuevoConductor) throws ConductorNoEncontradoException;

    void borrarConductor(Integer idusuario) throws ConductorNoEncontradoException;

    void ingresarImagen (MultipartFile imagen, Integer id) throws IOException, ConductorNoEncontradoException;

    Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws ConductorNoEncontradoException;
}
