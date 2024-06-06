package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario registrarUsuario(DatosUsuario usuario) throws UsuarioDuplicadoException {
        try{
            verificarDuplicados(usuario.getEmail(), usuario.getNombreUsuario());
            if(usuario.getTipoUsuario() == TipoUsuario.Conductor){
                Conductor conductor = usuario.toConductor();
                return usuarioRepositorio.registrarConductor(conductor);
            }else{
                Cliente cliente = usuario.toCliente();
                return usuarioRepositorio.registrarCliente(cliente);
            }
        }catch (Exception e){
            throw new UsuarioDuplicadoException("Usuario duplicado");
        }
    }

    private void verificarDuplicados(String email, String nombreUsuario) throws UsuarioDuplicadoException {
        Usuario duplicado = usuarioRepositorio.buscarDuplicados(email, nombreUsuario);
        if (duplicado != null) {
            throw new UsuarioDuplicadoException("Email o nombre de usuario ya existe");
        }
    }

}
