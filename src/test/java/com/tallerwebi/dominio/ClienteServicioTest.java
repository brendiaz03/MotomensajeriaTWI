package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.cliente.ClienteServicioImpl;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
    public void queSeCreeUnClienteQueSeLoPuedaBuscarPorSuIdYQueMeLoDevuelva() throws UsuarioNoEncontradoException {

        Cliente cliente = new Cliente();

        Integer id = 25;

        when(clienteRepositorio.obtenerClientePorId(id)).thenReturn(cliente);

        Cliente clienteBuscado = clienteServicio.obtenerClientePorId(id);

        assertThat(clienteBuscado, equalTo(cliente));
        assertThat(clienteBuscado.getId(), equalTo(cliente.getId()));

    }

    @Test
    public void dadoUnIdNuloDeberiaLanzarUsuarioNoEncontradoException() throws UsuarioNoEncontradoException {

        Integer idNulo = null;

        assertThrows(UsuarioNoEncontradoException.class, () -> {
            clienteServicio.obtenerClientePorId(idNulo);
        });

    }
}