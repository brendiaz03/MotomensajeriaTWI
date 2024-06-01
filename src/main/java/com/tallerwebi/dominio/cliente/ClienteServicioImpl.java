package com.tallerwebi.dominio.cliente;

import com.tallerwebi.dominio.usuario.TipoUsuario;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.presentacion.Datos.DatosRegistro;
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
    public Cliente registrarClienteNoDuplicado(DatosRegistro cliente) {
        Cliente clienteObtenido = this.clienteRepositorio.buscarDuplicados(cliente.getEmail(), cliente.getNombreUsuario());
        Cliente clienteAGuardar = this.mapearACliente(cliente);

        if (clienteObtenido == null) {
            return this.clienteRepositorio.registrarCliente(clienteAGuardar);
        }
        return null;
    }

    private Cliente mapearACliente(DatosRegistro cliente) {
        return new Cliente(cliente.getNombre(), cliente.getApellido(), cliente.getNumeroDeDni(), cliente.getEmail(), cliente.getNumeroDeTelefono(), cliente.getNombreUsuario(), cliente.getPassword(), cliente.getDomicilio(), TipoUsuario.CLIENTE);
    }
}
