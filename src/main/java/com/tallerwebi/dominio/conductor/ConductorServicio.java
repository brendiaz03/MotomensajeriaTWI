package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.presentacion.Datos.DatosRegistro;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ConductorServicio {

    Conductor registrarConductorNoDuplicado(DatosRegistro nuevoConductor) throws Exception;

    Conductor obtenerConductorPorId(Integer id) throws ConductorNoEncontradoException;

    void editarConductor(Conductor nuevoConductor) throws ConductorNoEncontradoException;

    void borrarConductor(Integer idusuario);

    void ingresarImagen (MultipartFile imagen, Integer id) throws IOException, ConductorNoEncontradoException;

    Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws ConductorNoEncontradoException;

}
