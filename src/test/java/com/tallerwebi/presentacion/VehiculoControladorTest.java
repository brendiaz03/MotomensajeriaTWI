package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VehiculoControladorTest {

    private VehiculoControlador vehiculoControlador;
    private VehiculoServicio vehiculoServicio;
    private ImagenServicio imagenServicio;
    private ConductorServicio conductorServicio;
    private HttpSession httpSession;

    @BeforeEach
    public void init() {

        this.vehiculoServicio = mock(VehiculoServicio.class);

        this.imagenServicio = mock(ImagenServicio.class);

        this.conductorServicio = mock(ConductorServicio.class);

        this.httpSession = mock(HttpSession.class);

        this.vehiculoControlador = new VehiculoControlador(this.vehiculoServicio, this.imagenServicio, this.conductorServicio);

    }

    @Test
    public void queSePuedaVerElFormDelVehiculo() throws ConductorNoEncontradoException {

        Imagen imagen = new Imagen();

        Integer idUsuario = 1;

        Conductor conductorNuevo = new Conductor ();

        when(this.imagenServicio.getImagenByName(anyString())).thenReturn(imagen);

        when(this.httpSession.getAttribute("isEditForm")).thenReturn(true);

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        when(this.conductorServicio.obtenerConductorPorId(anyInt())).thenReturn(conductorNuevo);

        ModelAndView mav = this.vehiculoControlador.mostrarRegistroDelVehiculo(httpSession);

        assertThat(mav.getViewName(), equalToIgnoringCase("form-vehiculo"));
    }

    @Test
    public void queAlEditarUnVehiculoVayaAlPerfil() throws ConductorNoEncontradoException {

        Integer idUsuario = 1;
        Long idVehiculoA = 1L;
        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(idUsuario);
        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductorNuevo);
        when(this.httpSession.getAttribute("isEditForm")).thenReturn(true);
        when(this.httpSession.getAttribute("idVehiculo")).thenReturn(idVehiculoA);

        doNothing().when(vehiculoServicio).actualizarVehiculo(vehiculo);
        doNothing().when(this.httpSession).setAttribute("isEditForm", false);
        when(this.conductorServicio.RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo)).thenReturn(true);

        ModelAndView mav = this.vehiculoControlador.registrarVehiculo(vehiculo, httpSession);

        verify(vehiculoServicio, times(1)).actualizarVehiculo(vehiculo);
        verify(conductorServicio, times(1)).RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo);
        verify(httpSession, times(1)).setAttribute("isEditForm", false);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/perfil"));
    }

    @Test
    public void queAlEditarUnVehiculoVayaAlHome() throws ConductorNoEncontradoException {

        Integer idUsuario = 1;
        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(idUsuario);
        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductorNuevo);
        when(this.httpSession.getAttribute("isEditForm")).thenReturn(false);
        when(this.vehiculoServicio.registrarVehiculo(vehiculo)).thenReturn(vehiculo);

        when(this.conductorServicio.RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo)).thenReturn(true);

        ModelAndView mav = this.vehiculoControlador.registrarVehiculo(vehiculo, httpSession);

        verify(vehiculoServicio, times(1)).registrarVehiculo(vehiculo);
        verify(conductorServicio, times(1)).RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void queAlRegistrarseUnVehiculoMuestreError() throws ConductorNoEncontradoException {

        Integer idUsuario = 1;
        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(idUsuario);
        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductorNuevo);
        when(this.httpSession.getAttribute("isEditForm")).thenReturn(false);
        when(this.vehiculoServicio.registrarVehiculo(vehiculo)).thenReturn(null);

        ModelAndView mav = this.vehiculoControlador.registrarVehiculo(vehiculo, httpSession);

        verify(vehiculoServicio, times(1)).registrarVehiculo(vehiculo);
        verify(conductorServicio, never()).RelacionarVehiculoAConductor(anyInt(), any(Vehiculo.class));

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/registro-vehiculo"));
        assertNotNull(mav.getModel().get("error"));
        assertEquals("Patente Repetida", mav.getModel().get("error"));
    }

}
