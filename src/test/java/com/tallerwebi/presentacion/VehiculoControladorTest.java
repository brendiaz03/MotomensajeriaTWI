package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class VehiculoControladorTest {

    private HttpSession session;
    private VehiculoServicio vehiculoServicioMock;
    private ConductorServicio conductorServicioMock;
    private VehiculoControlador vehiculoControlador;

    @BeforeEach
    public void setUp() {
        session = mock(HttpSession.class);
        vehiculoServicioMock = mock(VehiculoServicio.class);
        conductorServicioMock = mock(ConductorServicio.class);
        vehiculoControlador = new VehiculoControlador(vehiculoServicioMock, conductorServicioMock);
    }

    @Test
    public void dadoUnUsuarioCuandoSeMuestraEditarVehiculoEntoncesRedirigeAFormVehiculo() {
        // Preparación
        HttpSession sessionMock = mock(HttpSession.class);

        // Ejecución
        String viewName = vehiculoControlador.mostrarEditarVehiculo(sessionMock);

        // Verificación
        assertThat(viewName, equalTo("redirect:/form-vehiculo"));
        verify(sessionMock, times(1)).setAttribute("isEditForm", true);
    }

    @Test
    void dadoUnUsuarioNoLogeadoCuandoSeMuestraRegistroDelVehiculoEntoncesMuestraFormVehiculoSinEstarLogeado() throws UsuarioNoEncontradoException {

        Boolean logeado = false;

        when(session.getAttribute("isEditForm")).thenReturn(true);

        when(session.getAttribute("estaLogeado")).thenReturn(logeado);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        Conductor conductor = new Conductor();

        when(conductorServicioMock.obtenerConductorPorId(1)).thenReturn(conductor);

        ModelAndView modelAndView = vehiculoControlador.mostrarRegistroDelVehiculo(new Vehiculo(), null, session);

        assertThat("form-vehiculo", equalTo(modelAndView.getViewName()));
        assertFalse((Boolean) modelAndView.getModel().get("estaLogeado"));
    }

    @Test
    void dadoUnUsuarioLogeadoSinErrorCuandoSeMuestraElRegistroDelVehiculoEntoncesMuestraFormVehiculo() throws UsuarioNoEncontradoException {

        when(session.getAttribute("isEditForm")).thenReturn(true);

        when(session.getAttribute("estaLogeado")).thenReturn(true);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        Conductor conductor = new Conductor();

        when(conductorServicioMock.obtenerConductorPorId(1)).thenReturn(conductor);

        ModelAndView modelAndView = vehiculoControlador.mostrarRegistroDelVehiculo(new Vehiculo(), "", session);

        assertThat("form-vehiculo", equalTo(modelAndView.getViewName()));
        assertNull(modelAndView.getModel().get("mensajeError"));
    }

    @Test
    void dadoUnUsuarioLogeadoConErrorCuandoSeMuestraElRegistroDelVehiculoEntoncesMuestraFormVehiculoConError() throws UsuarioNoEncontradoException {

        when(session.getAttribute("estaLogeado")).thenReturn(true);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(session.getAttribute("isEditForm")).thenReturn(false);

        Conductor conductor = new Conductor();

        when(conductorServicioMock.obtenerConductorPorId(1)).thenReturn(conductor);

        ModelAndView modelAndView = vehiculoControlador.mostrarRegistroDelVehiculo(new Vehiculo(), "Error", session);

        assertThat("form-vehiculo", equalTo(modelAndView.getViewName()));
        assertThat("Error", equalTo(modelAndView.getModel().get("mensajeError")));
    }


    @Test
    void dadoUnUsuarioNoEncontradoCuandoSeMuestraRegistroDelVehiculoEntoncesRedirigeConMensajeError() throws UsuarioNoEncontradoException {

        when(session.getAttribute("estaLogeado")).thenReturn(true);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(conductorServicioMock.obtenerConductorPorId(1)).thenThrow(new UsuarioNoEncontradoException("Usuario no encontrado"));

        ModelAndView modelAndView = vehiculoControlador.mostrarRegistroDelVehiculo(new Vehiculo(), null, session);

        assertThat("redirect:/*", equalTo(modelAndView.getViewName()));
        assertThat("Usuario no encontrado Por favor, vuelva a intentarlo.", equalTo(modelAndView.getModel().get("mensajeError")));
    }

    @Test
    void dadoUnUsuarioLogeadoYQueEstaEditandoElVehiculoEntoncesMuestraFormVehiculoParaEditar() throws UsuarioNoEncontradoException {

        when(session.getAttribute("estaLogeado")).thenReturn(true);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(session.getAttribute("isEditForm")).thenReturn(true);

        Conductor conductor = new Conductor();

        Vehiculo vehiculo = new Vehiculo();

        conductor.setVehiculo(vehiculo);

        when(conductorServicioMock.obtenerConductorPorId(1)).thenReturn(conductor);

        ModelAndView modelAndView = vehiculoControlador.mostrarRegistroDelVehiculo(new Vehiculo(), null, session);

        assertThat("form-vehiculo", equalTo(modelAndView.getViewName()));
        assertTrue((Boolean) modelAndView.getModel().get("isEditForm"));
        assertThat(vehiculo, equalTo(modelAndView.getModel().get("vehiculo")));

    }

    @Test
    void dadoUnUsuarioLogeadoSinVehiculoCuandoSeMuestraElRegistroDelVehiculoEntoncesMuestraFormVehiculoSinVehiculo() throws UsuarioNoEncontradoException {

        when(session.getAttribute("estaLogeado")).thenReturn(true);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(session.getAttribute("isEditForm")).thenReturn(false);

        Conductor conductor = new Conductor();

        when(conductorServicioMock.obtenerConductorPorId(1)).thenReturn(conductor);

        ModelAndView modelAndView = vehiculoControlador.mostrarRegistroDelVehiculo(new Vehiculo(), null, session);

        assertThat("form-vehiculo", equalTo(modelAndView.getViewName()));
        assertTrue((Boolean) modelAndView.getModel().get("noVehiculo"));
        assertNotNull(modelAndView.getModel().get("vehiculo"));
    }

    @Test
    void dadoUnUsuarioLogeadoCuandoRegistraUnVehiculoEntoncesRedirigeAHomeConductor() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {

        Vehiculo nuevoVehiculo = new Vehiculo();

        Vehiculo registradoVehiculo = new Vehiculo();

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(session.getAttribute("estaLogeado")).thenReturn(true);

        when(vehiculoServicioMock.registrarVehiculo(nuevoVehiculo)).thenReturn(registradoVehiculo);

        ModelAndView modelAndView = vehiculoControlador.registrarVehiculo(nuevoVehiculo, session);

        verify(conductorServicioMock).RelacionarVehiculoAConductor(1, registradoVehiculo);
        assertThat("redirect:/home-conductor", equalTo(modelAndView.getViewName()));

    }

    @Test
    void dadoUnUsuarioNoLogeadoCuandoRegistraUnVehiculoEntoncesRedirigeAHomeConRegistroExitoso() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {

        Vehiculo nuevoVehiculo = new Vehiculo();

        Vehiculo registradoVehiculo = new Vehiculo();

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(session.getAttribute("estaLogeado")).thenReturn(null);

        when(vehiculoServicioMock.registrarVehiculo(nuevoVehiculo)).thenReturn(registradoVehiculo);

        ModelAndView modelAndView = vehiculoControlador.registrarVehiculo(nuevoVehiculo, session);

        verify(conductorServicioMock).RelacionarVehiculoAConductor(1, registradoVehiculo);
        assertThat("redirect:/home?registroExitoso=true", equalTo(modelAndView.getViewName()));
    }

    @Test
    void dadoUnUsuarioLogeadoCuandoEditaUnVehiculoEntoncesRedirigeAPerfil() throws UsuarioNoEncontradoException {

        Vehiculo nuevoVehiculo = new Vehiculo();

        Conductor conductor = new Conductor();

        Vehiculo vehiculoExistente = new Vehiculo();

        vehiculoExistente.setId(1);

        conductor.setVehiculo(vehiculoExistente);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(session.getAttribute("isEditForm")).thenReturn(false);

        when(conductorServicioMock.obtenerConductorPorId(1)).thenReturn(conductor);

        ModelAndView modelAndView = vehiculoControlador.editarVehiculo(nuevoVehiculo, session);

        verify(vehiculoServicioMock).actualizarVehiculo(nuevoVehiculo);
        assertThat(1, equalTo(nuevoVehiculo.getId()));
        assertThat("redirect:/perfil", equalTo(modelAndView.getViewName()));
        assertFalse((Boolean) session.getAttribute("isEditForm"));
    }

    @Test
    void dadoUnUsuarioNoEncontradoCuandoEditaElVehiculoEntoncesRedirigeConMensajeError() throws UsuarioNoEncontradoException {

        Vehiculo nuevoVehiculo = new Vehiculo();

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(conductorServicioMock.obtenerConductorPorId(1)).thenThrow(new UsuarioNoEncontradoException("Usuario no encontrado"));

        ModelAndView modelAndView = vehiculoControlador.editarVehiculo(nuevoVehiculo, session);

        assertThat("redirect:/*", equalTo(modelAndView.getViewName()));
        assertThat("Usuario no encontrado Por favor, vuelva a intentarlo.", equalTo(modelAndView.getModel().get("mensajeError")));
    }

}