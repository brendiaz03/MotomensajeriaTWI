package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteServicioImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public Cliente obtenerClientePorId(Integer idusuario) {
        return this.clienteRepositorio.obtenerClientePorId(idusuario);
    }

    @Override
    public void editarCliente(Cliente clienteEditado) throws UsuarioNoEncontradoException {
        try{
            Cliente cliente = this.clienteRepositorio.obtenerClientePorId(clienteEditado.getId());
            clienteEditado.setTipoUsuario(cliente.getTipoUsuario());
            if(cliente.getImagenPerfil()==null){
                clienteEditado.setImagenPerfil(cliente.getImagenPerfil());
            }
            this.clienteRepositorio.editarCliente(clienteEditado);
        }catch (NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo editar al cliente ya que el mismo no existe.");
        }
    }

}
