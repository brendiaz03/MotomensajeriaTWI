package com.tallerwebi.dominio.conductor;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IServiceConductor {
    Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws Exception;

    Conductor obtenerConductorPorId(Integer id) throws ConductorNoEncontradoException;

    void editarConductor(Conductor nuevoConductor) throws ConductorNoEncontradoException;

    void borrarConductor(Integer idusuario);

//    void ingresarImagen (MultipartFile imagen, Integer id) throws IOException, ConductorNoEncontradoException;
}
