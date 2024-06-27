package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.cliente.ClienteServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testObtenerClienteExistente() {
        int idUsuario = 1;
        Cliente cliente = new Cliente();
        cliente.setId(idUsuario);
        cliente.setNombre("Juan");

        when(clienteRepositorio.obtenerClientePorId(idUsuario)).thenReturn(cliente);
        Cliente clienteObtenido = clienteServicio.obtenerClientePorId(idUsuario);

        assertNotNull(clienteObtenido);
        assertEquals(idUsuario, clienteObtenido.getId());
        assertEquals("Juan", clienteObtenido.getNombre());
        verify(clienteRepositorio, times(1)).obtenerClientePorId(idUsuario);
    }

    @Test
    public void testObtenerClienteNoExistente() {
        int idUsuario = 2;

        when(clienteRepositorio.obtenerClientePorId(idUsuario)).thenReturn(null);
        Cliente clienteObtenido = clienteServicio.obtenerClientePorId(idUsuario);

        assertNull(clienteObtenido);
        verify(clienteRepositorio, times(1)).obtenerClientePorId(idUsuario);
    }



}