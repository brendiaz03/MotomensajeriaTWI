package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.exceptions.ClienteNoEncontradoException;
import com.tallerwebi.dominio.exceptions.ViajeNoEncontradoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class ClienteControladorTest {

    private ClienteServicio clienteServicioMock;
    private ViajeServicio viajeServicioMock;
    private ClienteControlador clienteControlador;
    private HttpSession session;
    private ViajeControlador viajeControlador;
    private PaqueteServicio paqueteServicioMock;
    private MercadoPagoServicio mercadoPagoServicio;

    @BeforeEach
    public void setUp() {
        clienteServicioMock = mock(ClienteServicio.class);
        viajeServicioMock = mock(ViajeServicio.class);
        clienteControlador = new ClienteControlador(clienteServicioMock, viajeServicioMock);
        viajeControlador = new ViajeControlador(viajeServicioMock, clienteServicioMock, paqueteServicioMock, mercadoPagoServicio);
    }

    @Test
    public void testDadoQueUnClienteEstaLogueadoYConSuIDEnLaSessionMostrarHomeCliente() throws UsuarioNoEncontradoException, ClienteNoEncontradoException {
        // Preparación
        HttpSession sessionMock = mock(HttpSession.class);
        Integer idUsuario = 1;
        when(sessionMock.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        Cliente clienteMock = new Cliente();
        clienteMock.setId(idUsuario);
        when(clienteServicioMock.obtenerClientePorId(idUsuario)).thenReturn(clienteMock);
        List<Viaje> viajesCanceladosMock = new ArrayList<>();
        when(viajeServicioMock.obtenerViajesCanceladosDelCliente(idUsuario)).thenReturn(viajesCanceladosMock);

        // Ejecución
        ModelAndView modelAndView = clienteControlador.mostrarHomeCliente(sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("home-cliente"));
        assertThat(modelAndView.getModel().get("cliente"), sameInstance(clienteMock));
        assertThat(modelAndView.getModel().containsKey("hayViajesCancelados"), is(false));
    }

    @Test
    public void testDadoQueElClienteNoTieneEnviosEnProcesoMostrarPantallaEnviosEnProceso() throws UsuarioNoEncontradoException, ClienteNoEncontradoException {
        // Preparación
        HttpSession sessionMock = mock(HttpSession.class);
        Integer idUsuario = 1;
        when(sessionMock.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        Cliente clienteMock = new Cliente();
        clienteMock.setId(idUsuario);
        when(clienteServicioMock.obtenerClientePorId(idUsuario)).thenReturn(clienteMock);
        List<Viaje> viajesEnProcesoMock = new ArrayList<>();
        when(viajeServicioMock.obtenerViajesEnProcesoDelCliente(idUsuario)).thenReturn(viajesEnProcesoMock);

        // Ejecución
        ModelAndView modelAndView = clienteControlador.mostrarEnviosEnProceso(sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("envios-en-proceso"));
        assertThat(modelAndView.getModel().get("cliente"), sameInstance(clienteMock));
        assertThat(modelAndView.getModel().containsKey("sinEnviosEnProceso"), is(true));
    }

    @Test
    public void testDadoQueElClienteCancelaEnvioQueLoRedirijaAEnviosEnProceso() throws ViajeNoEncontradoException {
        // Preparación
        Integer idViaje = 1;
        Viaje viajeMock = new Viaje();
        when(viajeServicioMock.buscarViaje(idViaje)).thenReturn(viajeMock);

        // Ejecución
        ModelAndView modelAndView = clienteControlador.cancelarEnvio(idViaje);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("redirect:/envios-en-proceso"));
        verify(viajeServicioMock, times(1)).cancelarEnvio(viajeMock);
    }

    @Test
    public void testDadoUnIdViajeExistenteCuandoSeDuplicaViajeCanceladoEntoncesDeberiaRedirigirAHomeCliente() throws ViajeNoEncontradoException {

        Integer idViaje = 1;

        Viaje viaje = new Viaje();

        when(viajeServicioMock.obtenerViajePorId(idViaje)).thenReturn(viaje);

        ModelAndView modelAndView = clienteControlador.duplicarViajeCancelado(idViaje);

        verify(viajeServicioMock).obtenerViajePorId(idViaje);
        verify(viajeServicioMock).actualizarViajeCancelado(viaje);
        verify(viajeServicioMock).duplicarViajeCancelado(viaje);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/home-cliente"));

    }

    @Test
    public void testDadoUnIdViajeInexistenteCuandoSeDuplicaViajeCanceladoEntoncesDeberiaLanzarViajeNoEncontradoException() throws ViajeNoEncontradoException {

        Integer idViaje = 1;

        when(viajeServicioMock.obtenerViajePorId(idViaje)).thenThrow(new ViajeNoEncontradoException("Viaje no encontrado"));

        assertThrows(ViajeNoEncontradoException.class, () -> {
            clienteControlador.duplicarViajeCancelado(idViaje);
        });

        verify(viajeServicioMock).obtenerViajePorId(idViaje);
        verify(viajeServicioMock, never()).actualizarViajeCancelado(any(Viaje.class));
        verify(viajeServicioMock, never()).duplicarViajeCancelado(any(Viaje.class));
    }

}