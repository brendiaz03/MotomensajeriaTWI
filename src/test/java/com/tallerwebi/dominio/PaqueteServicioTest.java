package com.tallerwebi.dominio;
import com.tallerwebi.dominio.paquete.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

        Paquete paquete = new Paquete();

        Paquete paqueteEsperado = new Paquete();
        try{

            paquete.setId(22);

            when(this.paqueteServicio.guardarPaquete(paquete)).thenReturn(paquete);

            paqueteEsperado = this.paqueteServicio.guardarPaquete(paquete);

        }catch (PaqueteNoEncontradoException e){

            throw new PaqueteNoEncontradoException();

        }

        assertThat(paqueteEsperado, equalTo(paquete));
        assertThat(paqueteEsperado.getId(), equalTo(paquete.getId()));
    }

    @Test
    public void queSePuedaActualizarUnPaquete() {
        Paquete paquete = new Paquete();
        paquete.setId(22);
        paquete.setPeso(10.0);

        paqueteServicio.editarPaquete(paquete);

        assertThat(paquete.getId(), equalTo(22));
        assertThat(paquete.getPeso(), equalTo(10.0));
        verify(paqueteRepositorio).editarPaquete(paquete);
    }

    @Test
    public void queSeObtengaUnPaquetePorSuId() throws PaqueteNoEncontradoException {

        Paquete paquete = new Paquete();

        Paquete paqueteEsperado = new Paquete();

        try{

            paquete.setId(29);

            when(paqueteRepositorio.obtenerPaquetePorId(paquete.getId())).thenReturn(paquete);

            paqueteEsperado = this.paqueteServicio.obtenerPaquetePorId(paquete.getId());

        }catch (PaqueteNoEncontradoException e){
            throw new PaqueteNoEncontradoException();
        }

        assertThat(paqueteEsperado, equalTo(paquete));
        assertThat(paquete.getId(), equalTo(paqueteEsperado.getId()));

    }

}
