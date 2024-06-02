package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.usuario.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.presentacion.Datos.DatosRegistro;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ConductorServicio {

    Conductor registrarConductorNoDuplicado(DatosRegistro nuevoConductor) throws Exception;

    Conductor obtenerConductorPorId(Integer id) throws UsuarioNoEncontradoException;

    void editarConductor(Conductor nuevoConductor) throws UsuarioNoEncontradoException;

    void borrarConductor(Integer idusuario) throws UsuarioNoEncontradoException;

    void ingresarImagen (MultipartFile imagen, Integer id) throws IOException, UsuarioNoEncontradoException;

    Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws UsuarioNoEncontradoException;

}
