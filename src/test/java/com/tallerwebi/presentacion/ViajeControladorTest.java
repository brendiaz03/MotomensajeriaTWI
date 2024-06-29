package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViajeControladorTest {

    private ViajeServicio viajeServicio;
    private ConductorServicio conductorServicio;
    private HttpSession session;
    private ViajeControlador viajeControlador;
    private HttpServletRequest request;
    private ClienteServicio clienteServicio;
    private PaqueteServicio paqueteServicio;


    @BeforeEach
    public void init() {
        this.viajeServicio = mock(ViajeServicio.class);
        this.conductorServicio = mock(ConductorServicio.class);
        this.clienteServicio = mock(ClienteServicio.class);
        this.session = mock(HttpSession.class);
        this.request = mock(HttpServletRequest.class);
        this.paqueteServicio = mock(PaqueteServicio.class);
        this.viajeControlador = new ViajeControlador(this.viajeServicio, this.conductorServicio, this.clienteServicio, this.paqueteServicio);
    }

    @Test
    public void crearViajeConPaqueteYCliente() throws PaqueteNoEncontradoException {
        Integer idUsuario = 1;
        Cliente cliente = new Cliente();
        Paquete paquete = new Paquete();
        Viaje viaje = new Viaje();
        viaje.setPrecio(100.0);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(clienteServicio.obtenerClientePorId(idUsuario)).thenReturn(cliente);

        String viajeObtenido = viajeControlador.crearViajeConPaqueteYCliente(session);

        verify(paqueteServicio).guardarPaquete(paquete);
        verify(viajeServicio).crearViaje(cliente, viaje, paquete);

        assertEquals("redirect:/pagar?precio=100.0", viajeObtenido);
    }

    @Test
    public void crearViajeConPaqueteYClientePaqueteNoEncontrado() throws PaqueteNoEncontradoException {
        Integer idUsuario = 1;
        Cliente cliente = new Cliente();
        Paquete paquete = new Paquete();
        Viaje viaje = new Viaje();
        viaje.setPrecio(100.0);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(clienteServicio.obtenerClientePorId(idUsuario)).thenReturn(cliente);

        doThrow(new PaqueteNoEncontradoException()).when(paqueteServicio).guardarPaquete(paquete);

        viajeControlador.crearViajeConPaqueteYCliente(session);
    }

    @Test
    public void mostrarFormViaje() {
        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(null);
        when(session.getAttribute("paqueteActual")).thenReturn(null);
        when(session.getAttribute("pasoActual")).thenReturn(null);

        ModelAndView modelAndView = viajeControlador.mostrarFormViaje(session);

        ModelMap model = modelAndView.getModelMap();
        assertEquals("form-viaje", modelAndView.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(1, model.get("pasoActual"));
        assertNotNull(model.get("viaje"));
        assertNotNull(model.get("paquete"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElViaje() {
        Viaje viaje = new Viaje();
        Paquete paquete = new Paquete();

        when(session.getAttribute("isEditViaje")).thenReturn(true);
        when(session.getAttribute("isEditPackage")).thenReturn(true);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        ModelAndView modelAndView = viajeControlador.mostrarFormViaje(session);

        ModelMap model = modelAndView.getModelMap();
        assertEquals("form-viaje", modelAndView.getViewName());
        assertEquals(true, model.get("isEditViaje"));
        assertEquals(true, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(3, model.get("pasoActual"));
        assertEquals(paquete, model.get("paquete"));
    }

    @Test
    public void mostrarFormEditarViaje() {
        ModelAndView modelAndView = viajeControlador.mostrarFormEditorViaje(session);

        verify(session).setAttribute("isEditViaje", true);
        verify(session).setAttribute("pasoActual", 2);

        assertEquals("redirect:/form-viaje", modelAndView.getViewName());
    }

    @Test
    public void mostrarFormSinQueElUsuarioEditeElViaje() {
        Viaje viaje = new Viaje();

        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(null);
        when(session.getAttribute("pasoActual")).thenReturn(2);

        ModelAndView modelAndView = viajeControlador.mostrarFormViaje(session);

        ModelMap model = modelAndView.getModelMap();
        assertEquals("form-viaje", modelAndView.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(2, model.get("pasoActual"));
        assertEquals(viaje, model.get("viaje"));
        assertNotNull(model.get("paquete"));
    }

    @Test
    public void queSePuedaEditarUnViaje() {
        Viaje viaje = new Viaje();
        viaje.setDomicilioDeLlegada("San Justo");
        viaje.setFecha(LocalDateTime.of(2024, 12, 1, 0, 0));
        viaje.setPrecio(10.0);

        ModelAndView modelAndView = viajeControlador.editarViaje(viaje, session);

        verify(session).setAttribute("isEditViaje", false);
        verify(session).setAttribute("viajeActual", viaje);
        verify(session).setAttribute("pasoActual", 3);

        assertEquals("redirect:/form-viaje", modelAndView.getViewName());
    }
}