package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ConductorServicio {

    Conductor obtenerConductorPorId(Integer id) throws UsuarioNoEncontradoException;

    Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws UsuarioNoEncontradoException;

}
