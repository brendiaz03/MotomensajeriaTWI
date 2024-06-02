package com.tallerwebi.dominio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteRepositorio;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.paquete.PaqueteServicioImpl;
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
    public void queSePuedaGuardarUnPaqueteYMeDevuelvaUnPaquete(){

        Paquete paquete = new Paquete();

        paquete.setId(22);

        when(this.paqueteServicio.guardarPaquete(paquete)).thenReturn(paquete);

        Paquete paqueteEsperado = this.paqueteServicio.guardarPaquete(paquete);

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
    public void queSeObtengaUnPaquetePorSuId(){

        Paquete paquete = new Paquete();

        paquete.setId(29);

        when(paqueteRepositorio.obtenerPaquetePorId(paquete.getId())).thenReturn(paquete);

        Paquete paqueteEsperado = this.paqueteServicio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteEsperado, equalTo(paquete));
        assertThat(paquete.getId(), equalTo(paqueteEsperado.getId()));
    }

}
