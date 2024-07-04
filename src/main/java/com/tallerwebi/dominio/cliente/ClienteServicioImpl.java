package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Base64;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteServicioImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public Cliente obtenerClientePorId(Integer idusuario) throws UsuarioNoEncontradoException {
        try{
            return this.clienteRepositorio.obtenerClientePorId(idusuario);
        }catch(NoResultException e){
        throw new UsuarioNoEncontradoException("No se encontro al usuario.");
    }
}
}
