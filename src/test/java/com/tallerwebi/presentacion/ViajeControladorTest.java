package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.exceptions.*;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ViajeControladorTest {

    private ViajeServicio viajeServicio;
    private HttpSession session;
    private ViajeControlador viajeControlador;
    private HttpServletRequest request;
    private ClienteServicio clienteServicio;
    private PaqueteServicio paqueteServicio;
    private MercadoPagoServicio mercadoPagoServicio;
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void init() {
        this.viajeServicio = mock(ViajeServicio.class);
        this.clienteServicio = mock(ClienteServicio.class);
        this.session = mock(HttpSession.class);
        this.request = mock(HttpServletRequest.class);
        this.paqueteServicio = mock(PaqueteServicio.class);
        this.mercadoPagoServicio = mock(MercadoPagoServicio.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
        this.viajeControlador = new ViajeControlador(this.viajeServicio, this.clienteServicio, this.paqueteServicio, this.mercadoPagoServicio);
    }

    @Test
    public void mostrarFormViaje() {
        // Preparación
        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(null);
        when(session.getAttribute("paqueteActual")).thenReturn(null);
        when(session.getAttribute("pasoActual")).thenReturn(null);

        // Ejecución
        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        // Validación
        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(1, model.get("pasoActual"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElViajeYElPaquete() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        when(session.getAttribute("isEditViaje")).thenReturn(true);
        when(session.getAttribute("isEditPackage")).thenReturn(true);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        // Ejecución
        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        // Validación
        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(true, model.get("isEditViaje"));
        assertEquals(true, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElViaje() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        when(session.getAttribute("isEditViaje")).thenReturn(true);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        // Ejecución
        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        // Validación
        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(true, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(paquete, model.get("paquete"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElPaquete() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(true);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        // Ejecución
        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        // Validación
        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(true, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
    }

    @Test
    public void mostrarFormViajeYQueElUsuarioNoEditeElViajeNiElPaquete() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(2);

        // Ejecución
        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        // Validación
        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
    }

    @Test
    public void mostrarFormEditarViaje() {
        // Preparación
        ModelAndView mav = viajeControlador.mostrarFormEditorViaje(session);

        // Ejecución
        verify(session).setAttribute("isEditViaje", true);
        verify(session).setAttribute("pasoActual", 2);

        // Validación
        assertEquals("redirect:/form-viaje", mav.getViewName());
    }

    @Test
    public void queSePuedaEditarUnViaje() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setDomicilioDeLlegada("San Justo");
        when(request.getSession()).thenReturn(session);

        // Ejecución
        ModelAndView mav = viajeControlador.editarViaje(viaje, session);

        // Validación
        verify(session).setAttribute("isEditViaje", false);
        verify(session).setAttribute("viajeActual", viaje);
        verify(session).setAttribute("pasoActual", 3);
        assertEquals("redirect:/form-viaje", mav.getViewName());
    }

    @Test
    public void queNoSePuedaEditarUnViajeSiEsNulo() {
        // Preparación
        Viaje viaje = dadoQueNoExisteUnViaje();

        // Ejecución
        ModelAndView mav = viajeControlador.editarViaje(viaje, session);

        // Validación
        assertThat("Viaje no encontrado", equalTo(mav.getModelMap().get("mensajeError")));
        assertThat("redirect:/*", equalTo(mav.getViewName()));
    }

    @Test
    public void queSePuedaCrearUnViajeLocalmente() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setDomicilioDeSalida("San Justo");

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeLocalmente(viaje, session);

        // Validación
        verify(session).setAttribute("viajeActual", viaje);
        verify(session).setAttribute("pasoActual", 3);
        assertEquals("redirect:/form-viaje", mav.getViewName());
    }

    @Test
    public void queNoSePuedaCrearUnViajeLocalmenteSiElViajeEsNulo() {
        // Preparación
        Viaje viaje = dadoQueNoExisteUnViaje();

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeLocalmente(viaje, session);

        // Validación
        assertThat("Viaje no encontrado", equalTo(mav.getModelMap().get("mensajeError")));
        assertThat("redirect:/*", equalTo(mav.getViewName()));
    }

    @Test
    public void queSePuedaCrearUnViajeConUnPaqueteYUnClienteAsignado() throws UsuarioNoEncontradoException, ViajeNoEncontradoException, PaqueteNoEncontradoException, ClienteNoEncontradoException, PrecioInvalidoException {
        // Preparación
        Integer idUsuario = 1;
        Cliente cliente = dadoQueExisteUnCliente();
        Paquete paquete = dadoQueExisteUnPaquete();
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setPrecio(100.0);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(clienteServicio.obtenerClientePorId(idUsuario)).thenReturn(cliente);

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeConPaqueteYCliente(session);

        // Validación
        verify(paqueteServicio).guardarPaquete(paquete);
        verify(viajeServicio).crearViaje(cliente, viaje, paquete);
        assertEquals("redirect:/pagar?precio=100.0", mav.getViewName());
    }

    @Test
    public void queNoSePuedaCrearUnViajeConUnPaqueteYUnClienteAsignadoSiElClienteEsNulo() throws UsuarioNoEncontradoException {
        // Preparación
        Integer idUsuario = 1;
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(clienteServicio.obtenerClientePorId(idUsuario)).thenThrow(new UsuarioNoEncontradoException("No se encontro al usuario."));

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeConPaqueteYCliente(session);

        // Validación
        assertThat("No se encontro al usuario.", equalTo(mav.getModelMap().get("mensajeError")));
        assertThat("redirect:/*", equalTo(mav.getViewName()));
    }

    @Test
    public void queNoSePuedaCrearUnViajeConUnPaqueteYUnClienteSiElIdDelUsuarioNoExiste() throws UsuarioNoEncontradoException {
        // Preparación
        Integer idUsuario = null;
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(clienteServicio.obtenerClientePorId(idUsuario)).thenThrow(new UsuarioNoEncontradoException("No esta logueado"));

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeConPaqueteYCliente(session);

        // Validación
        assertThat("No esta logueado", equalTo(mav.getModelMap().get("mensajeError")));
        assertThat("redirect:/*", equalTo(mav.getViewName()));
    }

    @Test
    public void queNoSePuedaCrearUnViajeConUnPaqueteYUnClienteSiElPaqueteNoFueEncontrado() throws PaqueteNoEncontradoException {
        // Preparación
        Paquete paquete = dadoQueExisteUnPaquete();
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(paqueteServicio.guardarPaquete(paquete)).thenThrow(new PaqueteNoEncontradoException("No se pudo guardar el paquete en nuestro sistema"));

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeConPaqueteYCliente(session);

        // Validación
        assertThat("No se pudo guardar el paquete en nuestro sistema", equalTo(mav.getModelMap().get("mensajeError")));
        assertThat("redirect:/*", equalTo(mav.getViewName()));
    }

    @Test
    public void queNoSePuedeCrearElViajeSiElViajeEsNulo() throws ViajeNoEncontradoException, UsuarioNoEncontradoException, ClienteNoEncontradoException, PaqueteNoEncontradoException, PrecioInvalidoException {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        Viaje viaje = dadoQueExisteNoUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        Integer idUsuario = 1;
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(clienteServicio.obtenerClientePorId(idUsuario)).thenReturn(cliente);
        when(viajeServicio.crearViaje(cliente, viaje, paquete)).thenThrow(new ViajeNoEncontradoException("Los datos son nulos"));

        // Ejecución
        ModelAndView mav = viajeControlador.crearViajeConPaqueteYCliente(session);

        // Validación
        assertThat("Los datos son nulos", equalTo(mav.getModelMap().get("mensajeError")));
        assertThat("redirect:/*", equalTo(mav.getViewName()));
    }

    @Test
    public void queSePuedaPagarUnEnvio() throws Exception {
        // Preparación
        Double precio = 100.0;
        Integer idViaje = 1;
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setId(idViaje);
        String url = "redirect:/https://mercadopago.com.ar";
        when(mercadoPagoServicio.pagarViajeMp(precio)).thenReturn(url);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(viajeServicio.obtenerViajePorId(idViaje)).thenReturn(viaje);
        doNothing().when(viajeServicio).actualizarViaje(viaje);

        // Ejecución
        ModelAndView mav = viajeControlador.pagarViaje(precio, session);

        // Validación
        assertEquals("redirect:" + url, mav.getViewName());
    }

    @Test
    public void queNoSePuedaPagarUnEnvioSiElPrecioEsMenorACero() {
        // Preparación
        Double precio = -10.0;

        // Ejecución
        ModelAndView mav = viajeControlador.pagarViaje(precio, session);

        // Validación
        assertEquals("redirect:/*", mav.getViewName());
        assertThat("Valor Invalido", equalTo(mav.getModelMap().get("mensajeError")));
    }

    @Test
    public void queNoSePuedaPagarUnEnvioSiElPrecioEsNulo() {
        // Preparación
        Double precio = null;

        // Ejecución
        ModelAndView mav = viajeControlador.pagarViaje(precio, session);

        // Validación
        assertEquals("redirect:/*", mav.getViewName());
        assertThat("Valor Invalido", equalTo(mav.getModelMap().get("mensajeError")));
    }

    @Test
    public void queNoSePuedaPagarUnEnvioSiElViajeEsNulo() throws ViajeNoEncontradoException {
        // Preparación
        Integer idViaje = 1;
        Double precio = 100.0;
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setId(idViaje);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(viajeServicio.obtenerViajePorId(idViaje)).thenThrow(new ViajeNoEncontradoException("ID Invalido"));

        // Ejecución
        ModelAndView mav = viajeControlador.pagarViaje(precio, session);

        // Validación
        assertEquals("redirect:/*", mav.getViewName());
        assertThat("ID Invalido", equalTo(mav.getModelMap().get("mensajeError")));
    }

    @Test
    public void queNoSePuedaPagarUnEnvioSiElPrecioEsNuloSiLaUrlEsInvalida() throws IOException {
        // Preparación
        when(mercadoPagoServicio.pagarViajeMp(100.0)).thenThrow(new IOException("Error al procesar el pago"));

        // Ejecución
        ModelAndView mav = viajeControlador.pagarViaje(100.0, session);

        //Validación
        assertEquals("redirect:/*", mav.getViewName());
        assertThat("Error al procesar el pago", equalTo(mav.getModelMap().get("mensajeError")));
    }

    private static Viaje dadoQueExisteUnViaje() {
        return new Viaje();
    }

    private static Paquete dadoQueExisteUnPaquete() {
        return new Paquete();
    }

    private static Cliente dadoQueExisteUnCliente() {
        return new Cliente();
    }

    private Viaje dadoQueNoExisteUnViaje() {
        return null;
    }

    private Viaje dadoQueExisteNoUnViaje() {
        return null;
    }
}