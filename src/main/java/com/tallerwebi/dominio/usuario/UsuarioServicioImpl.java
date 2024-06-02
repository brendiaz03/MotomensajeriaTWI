package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.presentacion.Datos.DatosRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Conductor registrarConductorNoDuplicado(DatosRegistro nuevoUsuario) throws UsuarioDuplicadoException {
        Conductor conductorARegistrar = nuevoUsuario.toConductor(nuevoUsuario);
        try{
            this.usuarioRepositorio.buscarDuplicados(nuevoUsuario.getEmail(), nuevoUsuario.getNombreUsuario());
            throw new UsuarioDuplicadoException("E-mail o Usuario Duplicado");
        }catch(NoResultException e){
            return this.usuarioRepositorio.registrarConductor(conductorARegistrar);
        }
    }

    @Override
    public Cliente registrarClienteNoDuplicado(DatosRegistro nuevoUsuario) throws UsuarioDuplicadoException {
        Cliente clienteARegistrar = nuevoUsuario.toCliente(nuevoUsuario);
        try{
            this.usuarioRepositorio.buscarDuplicados(nuevoUsuario.getEmail(), nuevoUsuario.getNombreUsuario());
            throw new UsuarioDuplicadoException("E-mail o Usuario Duplicado");
        }catch(NoResultException e){
            return this.usuarioRepositorio.registrarCliente(clienteARegistrar);
        }
    }


}
