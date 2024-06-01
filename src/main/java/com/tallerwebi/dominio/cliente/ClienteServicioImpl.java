package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;
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
    public void crearViaje(Cliente cliente, Viaje viaje) {
        viaje.setCliente(cliente);
        this.clienteRepositorio.crearViaje(viaje);
    }

    @Override
    public Cliente obtenerClientePorId(Integer idusuario) {
        return this.clienteRepositorio.obtenerClientePorId(idusuario);
    }

    @Override
    public void guardarPaquete(Paquete paquete) {
        this.clienteRepositorio.guardarPaquete(paquete);
    }

}
