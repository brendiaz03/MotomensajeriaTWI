package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;

public interface ClienteServicio {
    Cliente obtenerClientePorId(Integer idusuario);

    void editarCliente(Cliente cliente) throws UsuarioNoEncontradoException;
}
