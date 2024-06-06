package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViajeControladorTest {

    private ViajeServicio viajeServicio;
    private ConductorServicio conductorServicio;
    private HttpSession httpSession;
    private ViajeControlador viajeControlador;
    private HttpServletRequest request;
    private ClienteServicio clienteServicio;

    @BeforeEach
    public void init() {
        this.viajeServicio = mock(ViajeServicio.class);
        this.conductorServicio = mock(ConductorServicio.class);
        this.clienteServicio = mock(ClienteServicio.class);
        this.httpSession = mock(HttpSession.class);
        this.request = mock(HttpServletRequest.class);
        this.viajeControlador = new ViajeControlador(this.viajeServicio, this.conductorServicio, this.clienteServicio);
    }

    @Test
    public void queSiElConductorApretaEnElBotonHistorialDeViajesSeLeMuestreLaVistaHistorialDeViajes() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        conductor.setId(1);
        List<DatosViaje> historialViajes = Arrays.asList(new DatosViaje(), new DatosViaje());

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(conductor.getId());
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerHistorialDeViajes(conductor)).thenReturn(historialViajes);

        // Ejecución
        ModelAndView modelAndView = viajeControlador.mostrarHistorial(request);

        // Validación
        assertEquals(modelAndView.getModel().get("isUsuarioLogueado"), true);
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(historialViajes, modelAndView.getModel().get("viajesObtenidos"));
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("historial-viajes"));
    }

    @Test
    public void queCuandoElConductorAcepteElViajeLoLLeveAlViajeConElMapa() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        conductor.setId(1);
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(1);
        //viaje.setAceptado(false);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(conductor.getId());
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajeAceptadoPorId(1)).thenReturn(viaje);
        doNothing().when(viajeServicio).aceptarViaje(viaje, conductor);

        // Ejecución
        ModelAndView modelAndView = viajeControlador.AceptarViaje(request, viaje.getIdViaje());

        // Validación
        assertEquals(modelAndView.getModel().get("isUsuarioLogueado"), true);
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(viaje, modelAndView.getModel().get("viaje"));
        assertEquals(viaje.getIdViaje(), modelAndView.getModel().get("idViaje"));
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viaje"));
    }

    @Test
    public void queElConductorAlApretarEnElBotonViajesEnProcesoLoLleveALaVistaViajesEnProceso() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        conductor.setId(1);
        List<Viaje> viajesEnProceso = Arrays.asList(new Viaje(), new Viaje());

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajesEnProceso(conductor)).thenReturn(viajesEnProceso);

        // Ejecución
        ModelAndView modelAndView = viajeControlador.verViajesEnProceso(request);

        // Validación
        assertEquals(modelAndView.getModel().get("isUsuarioLogueado"), true);
        assertEquals(conductor, modelAndView.getModel().get("conductor"));
        assertEquals(viajesEnProceso, modelAndView.getModel().get("viajesObtenidos"));
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viajes-aceptados"));
    }

    @Test
    public void queElConductorPuedaVerElViajeAceptadoDesdeLaVistaViaje() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        conductor.setId(1);
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(1);
        //viaje.setCancelado(false);
        //viaje.setTerminado(false);
        //viaje.setDescartado(false);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajeAceptadoPorId(1)).thenReturn(viaje);

        // Ejecución
        ModelAndView modelAndView = this.viajeControlador.verViaje(request, viaje.getIdViaje());

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viaje"));
        //assertEquals(modelAndView.getModel().get("isUsuarioLogueado"), true);
        //assertEquals(conductor, modelAndView.getModel().get("conductor"));
        //assertEquals(viaje, modelAndView.getModel().get("viaje"));
        /*assertEquals(logo, modelAndView.getModel().get("logo"));
        assertEquals(user, modelAndView.getModel().get("user"));
        assertEquals(auto, modelAndView.getModel().get("auto"));
        assertEquals(fondo, modelAndView.getModel().get("fondo"));
        assertEquals(botonPS, modelAndView.getModel().get("botonPS"));*/
    }

    @Test
    public void queSiElConductorCancelaElViajeLoRedirijaAlHome() {
        // Preparación
        Integer idViaje = 1;
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(idViaje);
        viaje.setEstado(TipoEstado.CANCELADO);

        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

        // Ejecución
        ModelAndView modelAndView = this.viajeControlador.cancelarViaje(idViaje);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void queSiElConductorTerminaElViajeLoRedirijaAlHome() {
        // Preparación
        Integer idViaje = 1;
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(idViaje);
        viaje.setEstado(TipoEstado.TERMINADO);

        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);
        doNothing().when(viajeServicio).terminarViaje(viaje);

        // Ejecución
        ModelAndView modelAndView = this.viajeControlador.terminarViaje(idViaje);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void queSiElConductorApretaEnElBotonVolverQueVuelvaAlHome() {
        // Ejecución
        ModelAndView modelAndView = this.viajeControlador.volverAlHome();

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void queCuandoElConductorDescarteUnViajeLoRedirijaAlHome() throws UsuarioNoEncontradoException {
        // Preparación
        Integer idViaje = 1;
        Conductor conductor = new Conductor();
        conductor.setId(1);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        doNothing().when(viajeServicio).descartarViaje(idViaje, conductor);
        when(viajeServicio.estaPenalizado(conductor)).thenReturn(false);

        // Ejecución
        ModelAndView mav = this.viajeControlador.descartarViaje(request, idViaje);

        // Validación
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
        assertThat(httpSession.getAttribute("IDUSUARIO"), equalTo(1));
        assertThat(conductorServicio.obtenerConductorPorId(1), equalTo(conductor));
        assertThat(viajeServicio.estaPenalizado(conductor), equalTo(false));
    }

    @Test
    public void queCuandoElConductorDescarteCincoViajesLoPenalize() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        conductor.setId(1);

        List<Viaje> viajes = new ArrayList<>();
        Viaje viaje = new Viaje();
        viaje.setId(1);
        viajes.add(viaje);

        Viaje viaje2 = new Viaje();
        viaje2.setId(2);
        viajes.add(viaje2);

        Viaje viaje3 = new Viaje();
        viaje3.setId(3);
        viajes.add(viaje3);

        Viaje viaje4 = new Viaje();
        viaje4.setId(4);
        viajes.add(viaje4);

        Viaje viaje5 = new Viaje();
        viaje5.setId(5);
        viajes.add(viaje5);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        doNothing().when(viajeServicio).descartarViaje(viaje.getId(), conductor);
        doNothing().when(viajeServicio).descartarViaje(viaje2.getId(), conductor);
        doNothing().when(viajeServicio).descartarViaje(viaje3.getId(), conductor);
        doNothing().when(viajeServicio).descartarViaje(viaje4.getId(), conductor);
        doNothing().when(viajeServicio).descartarViaje(viaje5.getId(), conductor);
        when(viajeServicio.estaPenalizado(conductor)).thenReturn(true);

        // Ejecución
        this.viajeControlador.descartarViaje(request, viaje.getId());
        this.viajeControlador.descartarViaje(request, viaje2.getId());
        this.viajeControlador.descartarViaje(request, viaje3.getId());
        this.viajeControlador.descartarViaje(request, viaje4.getId());
        ModelAndView mav = this.viajeControlador.descartarViaje(request, viaje5.getId());

        // Validación
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
        assertThat(httpSession.getAttribute("IDUSUARIO"), equalTo(1));
        assertThat(conductorServicio.obtenerConductorPorId(1), equalTo(conductor));
        assertThat(viajeServicio.estaPenalizado(conductor), equalTo(true));
    }
}