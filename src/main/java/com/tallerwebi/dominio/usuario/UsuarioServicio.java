package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.presentacion.Datos.DatosRegistro;

public interface UsuarioServicio {

    Conductor registrarConductorNoDuplicado(DatosRegistro nuevoUsuario) throws UsuarioDuplicadoException;

    Cliente registrarClienteNoDuplicado(DatosRegistro nuevoUsuario) throws UsuarioDuplicadoException;

}
