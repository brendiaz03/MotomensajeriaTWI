package com.tallerwebi.dominio;

import com.tallerwebi.dominio.paquete.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PaqueteServicioTest {

    private PaqueteServicio paqueteServicio;
    private PaqueteRepositorio paqueteRepositorio;

    @BeforeEach
    public void init(){
        this.paqueteRepositorio = mock(PaqueteRepositorio.class);
        this.paqueteServicio = new PaqueteServicioImpl(this.paqueteRepositorio);
    }

    @Test
    public void queSePuedaGuardarUnPaqueteYMeDevuelvaUnPaquete() throws PaqueteNoEncontradoException {

        Paquete paquete = mock(Paquete.class);
        paquete.setId(22);

        when(this.paqueteServicio.guardarPaquete(paquete)).thenReturn(paquete);
        Paquete paqueteEsperado = this.paqueteServicio.guardarPaquete(paquete);

        assertThat(paqueteEsperado, equalTo(paquete));
        assertThat(paqueteEsperado.getId(), equalTo(paquete.getId()));
    }

    @Test
    public void queSeObtengaUnPaquetePorSuId() throws PaqueteNoEncontradoException {

        Paquete paquete = new Paquete();
        paquete.setId(29);

        when(this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId())).thenReturn(paquete);
        Paquete paqueteEsperado = this.paqueteServicio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteEsperado, equalTo(paquete));
        assertThat(paqueteEsperado.getId(), equalTo(paquete.getId()));
        verify(this.paqueteRepositorio).obtenerPaquetePorId(paquete.getId());
    }


    @Test
    public void queSeBusqueAUnPaquetePorSuIdYNoSeLoEncuentre() throws PaqueteNoEncontradoException {

        Paquete paquete = mock(Paquete.class);

        when(this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId())).thenThrow(PaqueteNoEncontradoException.class);
        assertThrows(PaqueteNoEncontradoException.class, () -> {
            paqueteServicio.obtenerPaquetePorId(paquete.getId());
        });

        verify(paqueteRepositorio, times(1)).obtenerPaquetePorId(paquete.getId());
    }
}
