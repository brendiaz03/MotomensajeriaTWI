package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ViajeControladorTest {

    private ViajeServicio viajeServicio;
    private ConductorServicio conductorServicio;
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
        this.conductorServicio = mock(ConductorServicio.class);
        this.clienteServicio = mock(ClienteServicio.class);
        this.session = mock(HttpSession.class);
        this.request = mock(HttpServletRequest.class);
        this.paqueteServicio = mock(PaqueteServicio.class);
        this.mercadoPagoServicio = mock(MercadoPagoServicio.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
        this.viajeControlador = new ViajeControlador(this.viajeServicio, this.conductorServicio, this.clienteServicio, this.paqueteServicio, this.mercadoPagoServicio);
    }

    @Test
    public void mostrarFormViaje() {
        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(null);
        when(session.getAttribute("paqueteActual")).thenReturn(null);
        when(session.getAttribute("pasoActual")).thenReturn(null);

        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(1, model.get("pasoActual"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElViajeYElPaquete() {
        Viaje viaje = new Viaje();
        Paquete paquete = new Paquete();

        when(session.getAttribute("isEditViaje")).thenReturn(true);
        when(session.getAttribute("isEditPackage")).thenReturn(true);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(true, model.get("isEditViaje"));
        assertEquals(true, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElViaje() {
        Viaje viaje = new Viaje();
        Paquete paquete = new Paquete();

        when(session.getAttribute("isEditViaje")).thenReturn(true);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(true, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
        assertEquals(paquete, model.get("paquete"));
    }

    @Test
    public void mostrarFormYQueElUsuarioEditeElPaquete() {
        Viaje viaje = new Viaje();
        Paquete paquete = new Paquete();

        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(true);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(3);

        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(true, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
    }

    @Test
    public void mostrarFormViajeYQueElUsuarioNoEditeElViajeNiElPaquete() {
        Viaje viaje = new Viaje();
        Paquete paquete = new Paquete();

        when(session.getAttribute("isEditViaje")).thenReturn(false);
        when(session.getAttribute("isEditPackage")).thenReturn(false);
        when(session.getAttribute("viajeActual")).thenReturn(viaje);
        when(session.getAttribute("paqueteActual")).thenReturn(paquete);
        when(session.getAttribute("pasoActual")).thenReturn(2);

        ModelAndView mav = viajeControlador.mostrarFormViaje(session);

        ModelMap model = mav.getModelMap();
        assertEquals("form-viaje", mav.getViewName());
        assertEquals(false, model.get("isEditViaje"));
        assertEquals(false, model.get("isEditPackage"));
        assertEquals("AIzaSyDcPeOyMBqG_1mZgjpei_R2ficRigdkINg", model.get("clave"));
    }

    @Test
    public void mostrarFormEditarViaje() {
        ModelAndView mav = viajeControlador.mostrarFormEditorViaje(session);

        verify(session).setAttribute("isEditViaje", true);
        verify(session).setAttribute("pasoActual", 2);

        assertEquals("redirect:/form-viaje", mav.getViewName());
    }

    @Test
    public void queSePuedaEditarUnViaje() {
        Viaje viaje = new Viaje();
        viaje.setDomicilioDeLlegada("San Justo");
        viaje.setFecha(LocalDateTime.of(2024, 12, 1, 0, 0));
        when(request.getSession()).thenReturn(session);

        ModelAndView mav = viajeControlador.editarViaje(viaje, session);

        verify(session).setAttribute("isEditViaje", false);
        verify(session).setAttribute("viajeActual", viaje);
        verify(session).setAttribute("pasoActual", 3);
        assertEquals("redirect:/form-viaje", mav.getViewName());
    }

    @Test
    public void queSePuedaCrearUnViajeLocalmente() {
        // Preparación
        Viaje viaje = new Viaje();
        viaje.setDomicilioDeSalida("San Justo");
        viaje.setFecha(LocalDateTime.of(2024, 5, 28, 0, 0));

        // Ejecución
        ModelAndView modelAndView = viajeControlador.crearViajeLocalmente(viaje, session);

        // Verificación
        verify(session).setAttribute("viajeActual", viaje);
        verify(session).setAttribute("pasoActual", 3);
        assertEquals("redirect:/form-viaje", modelAndView.getViewName());
    }

    @Test
    public void queSePuedaCrearUnViajeConUnPaqueteYUnClienteAsignado() throws PaqueteNoEncontradoException {
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
    public void queNoSePuedaCrearUnViajeConUnPaqueteYUnClienteSiElPaqueteNoFueEncontrado() throws PaqueteNoEncontradoException {
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

        assertThrows(PaqueteNoEncontradoException.class, () -> {
            viajeControlador.crearViajeConPaqueteYCliente(session);
        });
    }

    @Test
    public void queSePuedaPagarUnEnvio() throws Exception {
        Double precio = 100.0;
        String redirectUrl = "http://example.com/redirect";

        // Configurar el comportamiento del servicio
        when(mercadoPagoServicio.pagarViajeMp(precio)).thenReturn(redirectUrl);

        // Ejecutar el método
        String result = viajeControlador.pagarViaje(precio, redirectAttributes);

        // Verificar el redireccionamiento
        assertEquals("redirect:" + redirectUrl, result);
    }

    @Test
    public void queNoSePuedaPagarUnEnvioSiElPrecioNoEsValido() {
        Double precio = -10.0;

        // Ejecutar el método
        String result = viajeControlador.pagarViaje(precio, redirectAttributes);

        // Verificar que se haya añadido el atributo flash
        verify(redirectAttributes).addFlashAttribute("error", "Precio inválido.");

        // Verificar el redireccionamiento
        assertEquals("redirect:/homeCliente", result);
    }

    @Test
    public void queTireExcepcionSiHayUnErrorAlPagar() throws Exception {
        Double precio = 100.0;

        // Configurar el comportamiento del servicio para lanzar una excepción
        when(mercadoPagoServicio.pagarViajeMp(precio)).thenThrow(new Exception("Error de prueba"));

        // Ejecutar el método
        String result = viajeControlador.pagarViaje(precio, redirectAttributes);

        // Verificar que se haya añadido el atributo flash con el mensaje de error
        verify(redirectAttributes).addFlashAttribute("error", "Error al procesar el pago: Error de prueba");

        // Verificar el redireccionamiento
        assertEquals("redirect:/homeCliente", result);
    }

    @Test
    public void testPagarViaje_PrecioInvalido() {
        String view = viajeControlador.pagarViaje(-1.0, redirectAttributes);
        verify(redirectAttributes).addFlashAttribute("error", "Precio inválido.");
        assertEquals("redirect:/homeCliente", view);
    }
}