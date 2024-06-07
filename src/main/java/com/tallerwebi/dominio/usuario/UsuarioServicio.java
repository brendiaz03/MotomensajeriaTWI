package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.presentacion.Datos.DatosUsuario;

public interface UsuarioServicio {

    Usuario registrarUsuario(DatosUsuario usuario) throws UsuarioDuplicadoException;
}
