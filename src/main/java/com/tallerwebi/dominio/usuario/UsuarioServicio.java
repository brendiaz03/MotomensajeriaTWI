package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioServicio {

    Usuario registrarUsuario(DatosUsuario usuario) throws UsuarioDuplicadoException;

    void actualizarUsuario(DatosUsuario usuario, TipoUsuario tipoUsuario) throws UsuarioNoEncontradoException;

    Usuario obtenerUsuarioPorId(Integer id) throws UsuarioNoEncontradoException;

    void ingresarImagen(MultipartFile imagen, Integer idUsuario) throws UsuarioNoEncontradoException;

    void borrarCuenta(Integer id) throws UsuarioNoEncontradoException;
}
