package com.tallerwebi.dominio;

import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.exceptions.NoSePudoGuardarElPaqueteException;
import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
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
    public void queSePuedaGuardarUnNuevoPaqueteYMeDevuelvaElMismo() throws PaqueteNoEncontradoException {

        Paquete paquete = mock(Paquete.class);

        when(this.paqueteServicio.guardarPaquete(paquete)).thenReturn(paquete);

        Paquete paqueteEsperado = this.paqueteServicio.guardarPaquete(paquete);

        assertThat(paqueteEsperado, equalTo(paquete));

        assertThat(paqueteEsperado.getId(), equalTo(paquete.getId()));

    }

    @Test
    public void queAlIntentarGuardarUnPaqueteNoSePuedaGuardarYMeLanceLaExcepcionNoSePudoGuardarElPaqueteException() {

        Paquete paquete = mock(Paquete.class);

        when(this.paqueteRepositorio.guardarPaquete(paquete)).thenAnswer(invocation -> { throw new PaqueteNoEncontradoException("No se pudo guardar el paquete"); });

        assertThrows(PaqueteNoEncontradoException.class, () -> {
            this.paqueteServicio.guardarPaquete(paquete);
        });

    }

    @Test
    public void queSePuedaBuscarUnPaquetePorSuIdYMeLoDevuelvaCorresctamente () throws PaqueteNoEncontradoException {

        Paquete paquete = mock(Paquete.class);

        paquete.setId(29);

        when(this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId())).thenReturn(paquete);

        Paquete paqueteEsperado = this.paqueteServicio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteEsperado, equalTo(paquete));
        assertThat(paqueteEsperado.getId(), equalTo(paquete.getId()));
        verify(this.paqueteRepositorio).obtenerPaquetePorId(paquete.getId());

    }

    @Test
    public void queSeBusqueAUnPaquetePorSuIdYNoSeLoEncuentreOcasionandoQueSeLanceUnPaqueteNoEncontradoException() throws PaqueteNoEncontradoException {

        Paquete paquete = mock(Paquete.class);

        paquete.setId(25);

        when(this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId())).thenAnswer(invocation -> { throw new Exception(); });

        assertThrows(PaqueteNoEncontradoException.class, () -> {
            this.paqueteServicio.obtenerPaquetePorId(paquete.getId());
        });

    }

}
