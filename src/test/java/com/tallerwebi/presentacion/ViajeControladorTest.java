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

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ViajeControladorTest {
    private ViajeServicio viajeServicio;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;
    private HttpSession httpSession;
    private ViajeControlador viajeControlador;
    private HttpServletRequest request;

    @BeforeEach
    public void init() {

        this.viajeServicio = mock(ViajeServicio.class);

        this.imagenServicio = mock(ImagenServicio.class);

        this.conductorServicio = mock(ConductorServicio.class);

        this.httpSession = mock(HttpSession.class);

        this.request = mock(HttpServletRequest.class);

        this.viajeControlador = new ViajeControlador(this.viajeServicio, this.conductorServicio, this.imagenServicio);

    }

    @Test
    public void testQueTeLLeveALaVistaDeHistorialDeViajes() throws ConductorNoEncontradoException {
        // preparacion
        Conductor conductor = new Conductor();
        conductor.setId(1);
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);

        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);

        List<Viaje> historialViajes = Arrays.asList(new Viaje(), new Viaje());
        when(viajeServicio.obtenerHistorialDeViajes(conductor)).thenReturn(historialViajes);

        // accion
        ModelAndView modelAndView = viajeControlador.mostrarHistorial(request);

        // test
        assertEquals("historial-viajes", modelAndView.getViewName());
        assertEquals(true, modelAndView.getModel().get("isUsuarioLogueado"));
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(logo, modelAndView.getModel().get("logo"));
        assertEquals(user, modelAndView.getModel().get("user"));
        assertEquals(auto, modelAndView.getModel().get("auto"));
        assertEquals(fondo, modelAndView.getModel().get("fondo"));
        assertEquals(botonPS, modelAndView.getModel().get("botonPS"));
        assertEquals(historialViajes, modelAndView.getModel().get("viajesObtenidos"));

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("historial-viajes"));
    }

    @Test
    public void testQueAcepteElViajeYTeLLeveAlViajeConElMapa() throws ConductorNoEncontradoException {
        // Preparación
        Integer idViaje = 1;
        Conductor conductor = new Conductor();
        conductor.setId(1);
        Viaje viaje = new Viaje();
        viaje.setId(idViaje);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);

        // Acción
        ModelAndView modelAndView = viajeControlador.AceptarViaje(request, idViaje);

        // Verificación
        assertEquals("viaje", modelAndView.getViewName());
        assertEquals(true, modelAndView.getModel().get("isUsuarioLogueado"));
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(viaje, modelAndView.getModel().get("viaje"));
        assertEquals(viaje.getId(), modelAndView.getModel().get("idViaje"));
        assertEquals(logo, modelAndView.getModel().get("logo"));
        assertEquals(user, modelAndView.getModel().get("user"));
        assertEquals(auto, modelAndView.getModel().get("auto"));
        assertEquals(fondo, modelAndView.getModel().get("fondo"));
        assertEquals(botonPS, modelAndView.getModel().get("botonPS"));

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viaje"));
    }

    @Test
    public void testQueApretarEnViajesEnProcesoTeLleve() throws ConductorNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        conductor.setId(1);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);

        List<Viaje> viajesEnProceso = Arrays.asList(new Viaje(), new Viaje());
        when(viajeServicio.obtenerViajesEnProceso(conductor)).thenReturn(viajesEnProceso);

        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);

        // Acción
        ModelAndView modelAndView = viajeControlador.verViajesEnProceso(request);

        // Verificación
        assertEquals("viajes-aceptados", modelAndView.getViewName());
        assertEquals(true, modelAndView.getModel().get("isUsuarioLogueado"));
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(viajesEnProceso, modelAndView.getModel().get("viajesObtenidos"));
        assertEquals(logo, modelAndView.getModel().get("logo"));
        assertEquals(user, modelAndView.getModel().get("user"));
        assertEquals(auto, modelAndView.getModel().get("auto"));
        assertEquals(fondo, modelAndView.getModel().get("fondo"));
        assertEquals(botonPS, modelAndView.getModel().get("botonPS"));

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viajes-aceptados"));
    }

    @Test
    public void testQueSePuedaVolverAVerUnViajeAceptadoDesdeViajesEnProceso() throws ConductorNoEncontradoException {
        // Preparación
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);

        Conductor conductor = new Conductor();
        conductor.setId(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);

        Viaje viaje = new Viaje();
        viaje.setId(1);
        when(viajeServicio.obtenerViajeAceptadoPorId(1)).thenReturn(viaje);

        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);

        // Acción
        ModelAndView modelAndView = this.viajeControlador.verViaje(request, 1);

        // Verificación
        assertEquals("viaje", modelAndView.getViewName());
        assertEquals((Boolean) modelAndView.getModel().get("isUsuarioLogueado"), true);
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(viaje, modelAndView.getModel().get("viaje"));
        assertEquals(logo, modelAndView.getModel().get("logo"));
        assertEquals(user, modelAndView.getModel().get("user"));
        assertEquals(auto, modelAndView.getModel().get("auto"));
        assertEquals(fondo, modelAndView.getModel().get("fondo"));
        assertEquals(botonPS, modelAndView.getModel().get("botonPS"));
    }

    @Test
    public void testSiCanceloElViajeVuelvaAlHome() {
        // Preparación
        Integer idViaje = 1;
        Viaje viaje = new Viaje();
        viaje.setId(idViaje);
        viaje.setCancelado(true);

        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

        // Acción
        ModelAndView modelAndView = this.viajeControlador.cancelarViaje(idViaje);

        // Verificación
        assertEquals(viaje.getCancelado(), true);
        assertEquals("redirect:/home", modelAndView.getViewName());
    }

    @Test
    public void testSiTerminoElViajeVuelvaAlHome() {
        // Preparación
        Integer idViaje = 1;
        Viaje viaje = new Viaje();
        viaje.setId(idViaje);
        viaje.setTerminado(true);

        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

        // Acción
        ModelAndView modelAndView = this.viajeControlador.cancelarViaje(idViaje);

        // Verificación
        assertEquals(viaje.getTerminado(), true);
        assertEquals("redirect:/home", modelAndView.getViewName());
    }

    @Test
    public void testSiApretoEnVolverVuelvaAlHome() {
        // Acción
        ModelAndView modelAndView = this.viajeControlador.volverAlHome();

        // Verificación
        assertEquals("redirect:/home", modelAndView.getViewName());
    }

}