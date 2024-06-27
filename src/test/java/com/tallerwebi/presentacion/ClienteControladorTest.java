package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteControladorTest {

    private ClienteServicio clienteServicio;
    private ViajeServicio viajeServicio;
    private ClienteControlador clienteControlador;
    private HttpSession session;

    @BeforeEach
    public void init() throws Exception {
        this.clienteServicio = mock(ClienteServicio.class);
        this.viajeServicio = mock(ViajeServicio.class);
        this.session = mock(HttpSession.class);
        this.clienteControlador = new ClienteControlador(this.clienteServicio, this.viajeServicio);
    }

    @Test
    public void queSeRendericeElHomeDelCliente() {
        session.setAttribute("IDUSUARIO", 1);
        String nombreEsperado="home-cliente";
        Cliente cliente = mock(Cliente.class);
        List<Viaje> viajesCancelados = mock(List.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(clienteServicio.obtenerClientePorId(1)).thenReturn(cliente);
        when(viajeServicio.obtenerViajesCanceladosDelCliente(1)).thenReturn(viajesCancelados);
        ModelAndView mav = clienteControlador.mostrarHomeCliente(session);

        assertEquals(nombreEsperado, mav.getViewName());
        assertThat(mav.getModel().get("cliente"), equalTo(cliente));
    }

    @Test
    public void queSeMuestrenLosEnviosEnProcesoDelCliente() {
        String nombreEsperado="envios-en-proceso";
        Cliente cliente=mock(Cliente.class);
        List<Viaje> viajesEnProceso = mock(List.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(clienteServicio.obtenerClientePorId(1)).thenReturn(cliente);
        when(viajeServicio.obtenerViajesEnProcesoDelCliente(anyInt())).thenReturn(viajesEnProceso);
        ModelAndView mav = clienteControlador.mostrarEnviosEnProceso(session);

        assertEquals(nombreEsperado, mav.getViewName());
        assertThat(mav.getModel().get("cliente"), equalTo(cliente));
        assertThat(mav.getModel().get("viajesEnProceso"), equalTo(viajesEnProceso));
    }

    @Test
    public void queUnClientePuedaCancelarUnEnvioPreviamenteCreado() {
        String nombreEsperado="redirect:/envios-en-proceso";
        Viaje viaje= mock(Viaje.class);

        when(viajeServicio.buscarViaje(1)).thenReturn(viaje);
        ModelAndView mav = clienteControlador.cancelarEnvio(1);

        assertEquals(nombreEsperado, mav.getViewName());
        verify(viajeServicio).buscarViaje(1);
        verify(viajeServicio).cancelarEnvío(viaje);
    }


    @Test
    public void queSeRendericeLaVistaDelHistorialDeEnviosConTodosLosEnviosFinalizadosDelCliente() {
        String nombreEsperado="historial-envios";
        Cliente cliente=mock(Cliente.class);
        List<Viaje> viajesObtenidos = mock(List.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(clienteServicio.obtenerClientePorId(1)).thenReturn(cliente);
        when(viajeServicio.obtenerHistorialDeEnvios(1)).thenReturn(viajesObtenidos);
        ModelAndView mav = clienteControlador.mostrarHistorialEnvios(session);

        assertEquals(nombreEsperado, mav.getViewName());
        assertThat(mav.getModel().get("cliente"), equalTo(cliente));
        assertThat(mav.getModel().get("viajesObtenidos"), equalTo(viajesObtenidos));
    }

    @Test
    public void queElClientePuedaVerElDetalleDeUnEnvioEnParticular() {
        String nombreEsperado="detalle-envio";
        Cliente cliente=mock(Cliente.class);
        Viaje viaje = mock(Viaje.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(clienteServicio.obtenerClientePorId(1)).thenReturn(cliente);
        when(viajeServicio.obtenerViajePorId(1)).thenReturn(viaje);
        ModelAndView mav = clienteControlador.mostrarDetalleDelEnvio(1,session);

        assertEquals(nombreEsperado, mav.getViewName());
        assertThat(mav.getModel().get("cliente"), equalTo(cliente));
        assertThat(mav.getModel().get("viaje"), equalTo(viaje));
    }

    @Test
    public void queCuandoUnClienteCanceleUnViajeEsteSeDupliqueEnLaBD() {
        String nombreEsperado="redirect:/homeCliente";
        Viaje viaje = mock(Viaje.class);

        when(viajeServicio.obtenerViajePorId(1)).thenReturn(viaje);
        ModelAndView mav = clienteControlador.duplicarViajeCancelado(1);

        assertEquals(nombreEsperado, mav.getViewName());
        verify(viajeServicio).actualizarViajeCancelado(viaje);
        verify(viajeServicio).duplicarViajeCancelado(viaje);
    }

    @Test
    public void queCuandoUnClienUnViajeEsteSeDupliqueEnLaBD() {
        String nombreEsperado="redirect:/homeCliente";
        Viaje viaje = mock(Viaje.class);

        when(viajeServicio.obtenerViajePorId(1)).thenReturn(viaje);
        ModelAndView mav = clienteControlador.noDuplicarViaje(1);

        assertEquals(nombreEsperado, mav.getViewName());
        verify(viajeServicio).noDuplicarViaje(viaje);
    }
}
