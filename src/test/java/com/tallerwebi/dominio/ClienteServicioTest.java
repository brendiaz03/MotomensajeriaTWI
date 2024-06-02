package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.cliente.ClienteServicioImpl;
import com.tallerwebi.dominio.viaje.Viaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ClienteServicioTest {

    private ClienteServicio clienteServicio;
    private ClienteRepositorio clienteRepositorio;

    @BeforeEach
    public void init(){
        this.clienteRepositorio = mock(ClienteRepositorio.class);
        this.clienteServicio = new ClienteServicioImpl(this.clienteRepositorio);
    }

    @Test
    public void queElClientePuedaCrearUnViaje(){
        // Preparación
        Cliente cliente = new Cliente();
        Viaje viaje = new Viaje();
        viaje.setId(1);
        doNothing().when(this.clienteRepositorio).crearViaje(viaje);
        when(this.clienteRepositorio.buscarViajePorId(viaje.getId())).thenReturn(viaje);

        // Ejecución
        this.clienteServicio.crearViaje(cliente, viaje);
        Viaje viajeObtenido = this.clienteRepositorio.buscarViajePorId(viaje.getId());

        // Validación
        verify(this.clienteRepositorio).crearViaje(viaje);
        assertThat(viajeObtenido.getId(), equalTo(viaje.getId()));
    }
}