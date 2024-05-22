package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControladorViajeTest {

    private ViajeControlador controladorViaje;
    private ViajeServicio viajeServicio;
    private ConductorServicio conductorServicio;
    private ImagenServicio imagenServicio;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    
    @BeforeEach
    public void init (){
        viajeServicio = mock(ViajeServicio.class);
        conductorServicio = mock(ConductorServicio.class);
        imagenServicio = mock(ImagenServicio.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        controladorViaje = new ViajeControlador(viajeServicio, conductorServicio, imagenServicio, requestMock);
    }

    @Test
    public void queTeLLeveALaVistaViajesAceptados() throws ConductorNoEncontradoException {
        String nombreEsperado = "viajes";
        List<Viaje> viajes = new ArrayList<>();
        Integer idConductor = 1;
        Boolean estaLogueado = true;
        Conductor conductor = new Conductor();
        Imagen imagen = new Imagen();
        when(sessionMock.getAttribute("isUsuarioLogueado")).thenReturn(estaLogueado);
        when(sessionMock.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(this.imagenServicio.getImagenByName(anyString())).thenReturn(imagen);
        when(this.conductorServicio.obtenerConductorPorId(anyInt())).thenReturn(conductor);
        when(this.viajeServicio.obtenerLosViajesAceptadosPorElConductor(anyInt())).thenReturn(viajes);

        ModelAndView mav = this.controladorViaje.mostrarVistaViajesAceptados(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }

    @Test
    public void siCanceloElViajeVuelvaAlHome(){
        String nombreEsperado = "redirect:/home";
        Viaje viaje = new Viaje();
        Integer idViaje = 1;
        when(this.viajeServicio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(anyInt(), anyInt())).thenReturn(viaje);

        ModelAndView mav = this.controladorViaje.cancelarViaje(requestMock, idViaje);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }

    @Test
    public void siApretoEnVolverVuelvaAlHome(){
        String nombreEsperado = "redirect:/home";

        ModelAndView mav = this.controladorViaje.volverAlHome();

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }

    @Test
    public void siAceptoUnViajeMeRedirijaAlHome(){
        String nombreEsperado = "redirect:/home";
        Viaje viaje = new Viaje();
        Integer idViaje = 1;
        when(this.viajeServicio.actualizarViajeConElIdDelConductorQueAceptoElViaje(anyInt(), anyInt())).thenReturn(viaje);

        ModelAndView mav = this.controladorViaje.aceptarViaje(requestMock, idViaje);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }

    @Test
    public void siApretoEnVerViajeMeMuestreLaVistaDelViajeConElMapa() throws ConductorNoEncontradoException {
        String nombreEsperado = "ver-viaje";
        Integer idConductor = 1;
        Boolean estaLogueado = true;
        Imagen imagen = new Imagen();
        Integer idViaje = 1;
        Conductor conductor = new Conductor();
        when(sessionMock.getAttribute("isUsuarioLogueado")).thenReturn(estaLogueado);
        when(sessionMock.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(this.conductorServicio.obtenerConductorPorId(anyInt())).thenReturn(conductor);
        when(this.imagenServicio.getImagenByName(anyString())).thenReturn(imagen);

        ModelAndView mav = controladorViaje.verVistaViaje(requestMock, idViaje);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }
}
