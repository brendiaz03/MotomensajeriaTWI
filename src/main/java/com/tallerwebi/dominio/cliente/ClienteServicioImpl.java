package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteServicioImpl implements ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteServicioImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public Cliente obtenerClientePorId(Integer idUsuario) throws UsuarioNoEncontradoException {
        if (idUsuario == null) {
            throw new UsuarioNoEncontradoException("No se encontro al usuario.");
        }

        return this.clienteRepositorio.obtenerClientePorId(idUsuario);
    }
}
