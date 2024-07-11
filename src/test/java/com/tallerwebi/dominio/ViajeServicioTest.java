package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.conductor.ConductorServicioImpl;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.exceptions.*;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.*;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ViajeServicioTest {

    private ViajeServicio viajeServicio;
    private ViajeRepositorio viajeRepositorio;

    @BeforeEach
    public void init() {
        this.viajeRepositorio = mock(ViajeRepositorio.class);
        this.viajeServicio = spy(new ViajeServicioImpl(this.viajeRepositorio));
    }

    @Test
    public void queSePuedaObtenerUnViajePorSuId() throws ViajeNoEncontradoException {
        // Preparación
        Integer idViaje = 1;
        Viaje viaje = dadoQueExisteUnViaje();
        when(this.viajeRepositorio.obtenerViajePorId(idViaje)).thenReturn(viaje);

        // Ejecución
        DatosViaje viajeObtenido = this.viajeServicio.obtenerViajeAceptadoPorId(idViaje);

        // Validación
        assertThat(viajeObtenido.getIdViaje(), equalTo(viaje.getId()));
        assertThat(viajeObtenido.getNombreDelCliente(), equalTo(viaje.getCliente().getNombre()));
        assertNotNull(viajeObtenido);
    }

    @Test
    void queNoSePuedaObtenerUnViajeSiElIdEsNulo() {
        // Preparación
        Integer idViaje = dadoQueNoExisteElObjeto();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.obtenerViajeAceptadoPorId(idViaje));

        // Validación
        assertThat("El ID no es valido", equalTo(excepcion.getMessage()));
    }

    @Test
    void queNoSePuedaObtenerUnViajeSiElNoSeEncuentraElViaje() {
        // Preparación
        Integer idViaje = 1;
        when(viajeRepositorio.obtenerViajePorId(idViaje)).thenReturn(null);

        // Ejecución
        ViajeNoEncontradoException exception = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.obtenerViajeAceptadoPorId(1));

        // Validación
        assertThat("No se encontro el viaje", equalTo(exception.getMessage()));
    }

    @Test
    public void queSePuedaObtenerElHistorialDeViajesDeUnConductor() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        List<Viaje> viajes = dadoQueExistenViajesEnProcesosConUnConductorAsignado(conductor);
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // Ejecución
        List<DatosViaje> viajesObtenidos = viajeServicio.obtenerHistorialDeViajesConductor(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(2));
    }

    @Test
    public void queNoDevuelvaNingunViajeSiUnConductorNoTieneUnHistorialDeViajes() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        List<Viaje> viajes = dadoQueExisteUnaListaDeViajesVacias();
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // Ejecución
        List<DatosViaje> viajesObtenidos = viajeServicio.obtenerHistorialDeViajesConductor(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(0));
        assertTrue(viajesObtenidos.isEmpty());
    }

    @Test
    public void queNoDevuelvaUnHistorialDeViajesSiElConductorEsNulo() {
        // Preparación
        Conductor conductor = dadoQueNoExisteUnConductor();

        // Ejecución
        UsuarioNoEncontradoException exception = assertThrows(UsuarioNoEncontradoException.class, () -> viajeServicio.obtenerHistorialDeViajesConductor(conductor));

        // Validación
        assertThat("No se encuentra logueado", equalTo(exception.getMessage()));
    }

    @Test
    public void queSePuedaObtenerLosViajesEnProcesoDeUnConductor() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        List<Viaje> viajesEnProceso = dadoQueExistenViajesEnProcesosConUnConductorAsignado(conductor);
        viajesEnProceso.get(3).setEstado(TipoEstado.ACEPTADO);
        viajesEnProceso.get(4).setEstado(TipoEstado.ACEPTADO);
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajesEnProceso);

        // Ejecución
        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(2));
        assertThat(viajesEnProceso.get(3).getDomicilioDeSalida(), equalTo(viajesObtenidos.get(0).getDomicilioDeSalida()));
        assertThat(viajesEnProceso.get(4).getDomicilioDeSalida(), equalTo(viajesObtenidos.get(1).getDomicilioDeSalida()));
        assertThat(viajesObtenidos.get(0).getDomicilioDeSalida(), equalTo("San Justo"));
        assertThat(viajesObtenidos.get(1).getDomicilioDeSalida(), equalTo("Marconi"));
        assertThat(viajesObtenidos.get(0).getDomicilioDeLlegada(), equalTo("Cañuelas"));
        assertThat(viajesObtenidos.get(1).getDomicilioDeLlegada(), equalTo("Villa 31"));
    }

    @Test
    public void queNoSePuedaObtenerLosViajesEnProcesoSiElConductorNoAceptoNinguno() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        List<Viaje> viajesEnProceso = dadoQueExistenViajesEnProcesosConUnConductorAsignado(conductor);
        viajesEnProceso.get(2).setEstado(TipoEstado.CANCELADO);
        viajesEnProceso.get(3).setEstado(TipoEstado.TERMINADO);
        viajesEnProceso.get(4).setEstado(TipoEstado.DESCARTADO);
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajesEnProceso);

        // Ejecución
        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(0));
        assertTrue(viajesObtenidos.isEmpty());
    }

    @Test
    public void queNoSePuedaObtenerLosViajesEnProcesoSiElConductorEsNulo() {
        // Preparación
        Conductor conductor = dadoQueNoExisteUnConductor();

        // Ejecución
        UsuarioNoEncontradoException exception = assertThrows(UsuarioNoEncontradoException.class, () -> viajeServicio.obtenerViajesEnProceso(conductor));

        // Validación
        assertThat("No se encuentra logueado", equalTo(exception.getMessage()));
    }

    @Test
    public void queSePuedaAceptarUnViaje() throws UsuarioNoEncontradoException, ViajeNoEncontradoException {
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        Conductor conductor = dadoQueExisteUnConductor();
        DatosViaje datosViaje = dadoQueExistenDatosViajes();
        datosViaje.setIdViaje(1);
        when(viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje())).thenReturn(viajeEsperado);
        viajeEsperado.setEstado(TipoEstado.ACEPTADO);
        viajeEsperado.setConductor(conductor);
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.aceptarViaje(datosViaje, conductor);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(datosViaje.getIdViaje());
        assertThat(viajeEsperado.getEstado(), equalTo(TipoEstado.ACEPTADO));
        verify(viajeRepositorio).editar(viajeEsperado);
    }

    @Test
    public void queNoSePuedaAceptarUnViajeSiElConductorEsNulo() {
        // Preparación
        Conductor conductor = dadoQueNoExisteUnConductor();
        DatosViaje datosViaje = dadoQueExistenDatosViajes();

        // Ejecución
        UsuarioNoEncontradoException exception = assertThrows(UsuarioNoEncontradoException.class, () -> viajeServicio.aceptarViaje(datosViaje, conductor));

        // Validación
        assertThat("No se encuentra logueado", equalTo(exception.getMessage()));
    }

    @Test
    public void queNoSePuedaAceptarUnViajeSiElDatosViajeEsNulo() {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        DatosViaje datosViaje = dadoQueNoExisteDatosViaje();

        // Ejecución
        ViajeNoEncontradoException exception = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.aceptarViaje(datosViaje, conductor));

        // Validación
        assertThat("Los datos son nulos", equalTo(exception.getMessage()));
    }

    @Test
    public void queNoSePuedaAceptarUnViajeSiElViajeObtenidoEsNulo() {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        DatosViaje datosViaje = dadoQueExistenDatosViajes();
        when(viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje())).thenReturn(null);

        // Ejecución
        ViajeNoEncontradoException exception = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.aceptarViaje(datosViaje, conductor));

        // Validación
        assertEquals("El viaje no existe", exception.getMessage());
    }

    @Test
    public void queSePuedaCancelarUnViajeee() throws ViajeNoEncontradoException {
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        Viaje datosViaje = dadoQueExisteUnViaje();
        Conductor conductor = dadoQueExisteUnConductor();
        viajeEsperado.setId(1);
        datosViaje.setId(1);
        datosViaje.setEstado(TipoEstado.PENDIENTE);
        datosViaje.setConductor(conductor);
        when(viajeRepositorio.obtenerViajePorId(viajeEsperado.getId())).thenReturn(viajeEsperado);
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.cancelarViaje(datosViaje);

        // Validación
        assertThat(viajeEsperado.getEstado(), equalTo(TipoEstado.CANCELADO));
        assertEquals(datosViaje.getConductor().getId(), viajeEsperado.getCanceladoPor());
    }

    @Test
    public void queNoSePuedaCancelarUnViajeSiElDatosViajesEsNulo() {
        // Preparación
        Viaje datosViaje = dadoQueNoExisteUnViaje();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.cancelarViaje(datosViaje));

        // Validación
        assertThat("Los datos son nulos", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queNoSePuedaCancelarUnViajeSiElViajeObtenidoEsNulo() {
        // Preparación
        Viaje datosViaje = dadoQueExisteUnViaje();
        when(viajeRepositorio.obtenerViajePorId(datosViaje.getId())).thenReturn(null);

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.cancelarViaje(datosViaje));

        // Validación
        assertThat("El viaje no existe", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queSePuedaTerminarUnViaje() throws ViajeNoEncontradoException {
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        DatosViaje viaje = dadoQueExistenDatosViajes();
        viaje.setIdViaje(1);
        when(viajeRepositorio.obtenerViajePorId(viaje.getIdViaje())).thenReturn(viajeEsperado);
        viajeEsperado.setEstado(TipoEstado.TERMINADO);
        viajeEsperado.setFecha(LocalDateTime.now());
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.terminarViaje(viaje);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getIdViaje());
        assertThat(viajeEsperado.getEstado(), equalTo(TipoEstado.TERMINADO));
        verify(viajeRepositorio).editar(viajeEsperado);
    }

    @Test
    public void queNoSePuedaTerminarUnViajeSiElDatoViajeEsNulo() {
        // Preparación
        DatosViaje viaje = dadoQueNoExisteDatosViaje();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.terminarViaje(viaje));

        // Validación
        assertThat("Los datos son nulos", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queNoSePuedaTerminarUnViajeSiElViajeObtenidoEsNulo() {
        // Preparación
        DatosViaje viaje = dadoQueExistenDatosViajes();
        when(viajeRepositorio.obtenerViajePorId(viaje.getIdViaje())).thenReturn(null);

        // Ejecución
        ViajeNoEncontradoException exception = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.terminarViaje(viaje));

        // Validación
        assertThat("El viaje no existe", equalTo(exception.getMessage()));
    }

    @Test
    public void queTireUnaExcepcionSiOcurreUnProblemaAlQuererTerminarUnViaje() {
        // Preparación
        DatosViaje datosViaje = dadoQueExistenDatosViajes();
        datosViaje.setIdViaje(1);
        Viaje viaje = dadoQueExisteUnViaje();
        when(viajeRepositorio.obtenerViajePorId(datosViaje.getIdViaje())).thenReturn(viaje);
        doThrow(new RuntimeException("Ocurrio un problema al querer terminar un viaje")).when(viajeRepositorio).editar(viaje);

        // Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> viajeServicio.terminarViaje(datosViaje));

        // Validación
        assertThat("Ocurrio un problema al querer terminar un viaje", equalTo(exception.getMessage()));
    }

    @Test
    public void queSePuedaCrearUnViaje() throws ViajeNoEncontradoException, ClienteNoEncontradoException, PaqueteNoEncontradoException, PrecioInvalidoException {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        when(viajeRepositorio.guardarViaje(viaje)).thenReturn(viaje);
        doReturn(2000.0).when(viajeServicio).calcularPrecio(viaje);

        // Ejecución
        Viaje viajeCreado = viajeServicio.crearViaje(cliente, viaje, paquete);

        // Validación
        assertNotNull(viajeCreado);
        assertEquals(cliente, viajeCreado.getCliente());
        assertEquals(paquete, viajeCreado.getPaquete());
        assertEquals(TipoEstado.PENDIENTE, viajeCreado.getEstado());
    }

    @Test
    public void queNoSePuedaCrearUnViajeSiElClienteEsNulo() {
        // Preparación
        Cliente cliente = dadoQueNoExisteUnCliente();
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();

        // Ejecución
        ClienteNoEncontradoException excepcion = assertThrows(ClienteNoEncontradoException.class, () -> viajeServicio.crearViaje(cliente, viaje, paquete));

        // Validación
        assertEquals("No esta logueado", excepcion.getMessage());
    }

    @Test
    public void queNoSePuedaCrearUnViajeSiElViajeEsNulo() {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        Viaje viaje = dadoQueNoExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.crearViaje(cliente, viaje, paquete));

        // Validación
        assertEquals("Los datos son nulos", excepcion.getMessage());
    }

    @Test
    public void queNoSePuedaCrearUnViajeSiElPaqueteEsNulo() {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueNoExisteUnPaquete();

        // Ejecución
        PaqueteNoEncontradoException excepcion = assertThrows(PaqueteNoEncontradoException.class, () -> viajeServicio.crearViaje(cliente, viaje, paquete));

        // Validación
        assertEquals("No se encontró el paquete buscado", excepcion.getMessage());
    }

    @Test
    public void queNoSePuedaCrearUnViajeSiElPrecioDelViajeEsNulo() {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        Viaje viaje = dadoQueExisteUnViaje();
        Paquete paquete = dadoQueExisteUnPaquete();
        doReturn(null).when(viajeServicio).calcularPrecio(any(Viaje.class));

        // Ejecución
        PrecioInvalidoException excepcion = assertThrows(PrecioInvalidoException.class, () -> viajeServicio.crearViaje(cliente, viaje, paquete));

        // Validación
        assertEquals("El precio es invalido o menor a 0", excepcion.getMessage());
    }

    @Test
    public void queSePuedaBuscarUnViajePorSuId() throws ViajeNoEncontradoException {
        // Preparación
        Integer idViaje = 1;
        Viaje viaje = dadoQueExisteUnViaje();
        when(viajeRepositorio.obtenerViajePorId(idViaje)).thenReturn(viaje);

        // Ejecución
        Viaje viajeObtenido = this.viajeServicio.buscarViaje(idViaje);

        // Validación
        assertThat(viajeObtenido.getId(), equalTo(viaje.getId()));
        assertThat(viajeObtenido.getCliente().getNombre(), equalTo(viaje.getCliente().getNombre()));
        assertThat(viajeObtenido.getDomicilioDeLlegada(), equalTo(viaje.getDomicilioDeLlegada()));
    }

    @Test
    public void queNoSePuedaBuscarUnViajePorSuIdSiElIdDelParametroEsNulo() {
        // Preparación
        Integer idViaje = dadoQueNoExisteElObjeto();

        // Ejecución
        ViajeNoEncontradoException exception = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.buscarViaje(idViaje));

        // Validación
        assertThat("ID Invalido", equalTo(exception.getMessage()));
    }

    @Test
    public void queNoSePuedaBuscarUnViajePorSuIdYDevolverloSiElViajeObtenidoEsNulo() {
        // Preparación
        Integer idViaje = 1;
        when(viajeRepositorio.obtenerViajePorId(idViaje)).thenReturn(null);

        // Ejecución
        ViajeNoEncontradoException exception = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.buscarViaje(idViaje));

        // Validación
        assertThat("Viaje no encontrado", equalTo(exception.getMessage()));
    }

    @Test
    public void queSePuedaObtenerLosViajesEnProcesoDelCliente() throws ClienteNoEncontradoException {
        // Preparación
        Integer idCliente = 1;
        Cliente cliente = dadoQueExisteUnCliente();
        cliente.setId(idCliente);
        List<Viaje> viajes = dadoQueExistenViajesCreadosPorElCliente(cliente);
        when(viajeRepositorio.obtenerViajesPorCliente(idCliente)).thenReturn(viajes);

        // Ejecución
        List<Viaje> viajesObtenidosDelCliente = this.viajeServicio.obtenerViajesEnProcesoDelCliente(idCliente);

        // Validación
        assertThat(viajesObtenidosDelCliente.size(), equalTo(viajes.size()));
    }

    @Test
    public void queNoSePuedaObtenerLosViajesEnProcesoDelClienteSiElIdPorParametroEsNulo() {
        // Preparación
        Integer idCliente = dadoQueNoExisteElObjeto();

        // Ejecución
        ClienteNoEncontradoException exception = assertThrows(ClienteNoEncontradoException.class, () -> viajeServicio.obtenerViajesEnProcesoDelCliente(idCliente));

        // Validación
        assertThat("ID Invalido", equalTo(exception.getMessage()));
    }

    @Test
    public void queSeRetorneUnaListaVaciaSiNoHayViajesEnProceso() throws ClienteNoEncontradoException {
        // Preparación
        Integer idUsuario = 1;
        List<Viaje> listaVaciaSinViajesEnProcesos = dadoQueExisteUnaListaDeViajesVacias();
        when(viajeRepositorio.obtenerViajesPorCliente(idUsuario)).thenReturn(listaVaciaSinViajesEnProcesos);

        // Ejecución
        List<Viaje> viajes = viajeServicio.obtenerViajesEnProcesoDelCliente(idUsuario);

        // Validación
        assertTrue(viajes.isEmpty());
    }

    @Test
    public void queSePuedaCancelarUnEnvio() throws ViajeNoEncontradoException {
        // Preparación
        Viaje viajeExistente = dadoQueExisteUnViaje();
        doNothing().when(viajeRepositorio).editar(viajeExistente);

        // Ejecución
        viajeServicio.cancelarEnvio(viajeExistente);

        // Validación
        verify(viajeRepositorio, times(1)).editar(viajeExistente);
    }

    @Test
    public void queNoSePuedaCancelarUnEnvioSiElViajeACancelarEsNulo() {
        // Preparación
        Viaje viaje = dadoQueNoExisteUnViaje();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.cancelarEnvio(viaje));

        // Validación
        assertThat("No se pudo cancelar el viaje", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queSePuedaObtenerLosViajesCanceladosDelCliente() throws ClienteNoEncontradoException {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        List<Viaje> viajesCancelados = dadoQueExistenViajesCanceladosPorElCliente(cliente);
        when(viajeRepositorio.obtenerViajesPorCliente(cliente.getId())).thenReturn(viajesCancelados);

        // Ejecución
        List<Viaje> viajesCanceladosObtenidos = this.viajeServicio.obtenerViajesCanceladosDelCliente(cliente.getId());

        // Validación
        assertThat(viajesCanceladosObtenidos.size(), equalTo(viajesCancelados.size()));
    }

    @Test
    public void queNoSePuedaObtenerLosViajesCanceladosDelClienteSiElIdPorParametroEsNulo() {
        // Preparación
        Integer idCliente = dadoQueNoExisteElObjeto();

        // Ejecución
        ClienteNoEncontradoException exception = assertThrows(ClienteNoEncontradoException.class, () -> viajeServicio.obtenerViajesCanceladosDelCliente(idCliente));

        // Validación
        assertThat("ID Invalido", equalTo(exception.getMessage()));
    }

    @Test
    public void queSeRetorneUnaListaVaciaSiNoHayViajesCanceladosPorElCliente() throws ClienteNoEncontradoException {
        // Preparación
        Integer idUsuario = 1;
        List<Viaje> listaVaciaSinViajesCancelados = dadoQueExisteUnaListaDeViajesVacias();
        when(viajeRepositorio.obtenerViajesPorCliente(idUsuario)).thenReturn(listaVaciaSinViajesCancelados);

        // Ejecución
        List<Viaje> viajes = viajeServicio.obtenerViajesCanceladosDelCliente(idUsuario);

        // Validación
        assertTrue(viajes.isEmpty());
    }

    @Test
    public void queSePuedaDuplicarUnViajeCancelado() throws ViajeNoEncontradoException {
        // Preparación
        Viaje viajeDuplicado = dadoQueExisteUnViaje();
        doNothing().when(viajeRepositorio).guardarViajeDuplicado(viajeDuplicado);

        // Ejecución
        viajeServicio.duplicarViajeCancelado(viajeDuplicado);

        // Validación
        verify(viajeRepositorio, times(1)).guardarViajeDuplicado(viajeDuplicado);
    }

    @Test
    public void queNoSePuedaDuplicarUnViajeCanceladoSiElViajeEsNulo() {
        // Preparación
        Viaje viajeDuplicado = dadoQueNoExisteUnViaje();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.duplicarViajeCancelado(viajeDuplicado));

        // Validación
        assertThat("Los datos son nulos", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queSePuedaDuplicarUnViajeDescartado() throws ViajeNoEncontradoException, ClienteNoEncontradoException {
        // Preparación
        Viaje viajeDuplicado = dadoQueExisteUnViaje();
        Conductor conductor = dadoQueExisteUnConductor();
        doNothing().when(viajeRepositorio).guardarViajeDuplicado(viajeDuplicado);

        // Ejecución
        viajeServicio.duplicarViajeDescartado(viajeDuplicado, conductor);

        // Validación
        assertEquals(TipoEstado.DESCARTADO, viajeDuplicado.getEstado());
        assertEquals(conductor, viajeDuplicado.getConductor());
        verify(viajeRepositorio, times(1)).guardarViajeDuplicado(viajeDuplicado);
    }

    @Test
    public void queNoSePuedaDuplicarUnViajeDescartadoSiElViajeADuplicarEsNulo() {
        // Preparación
        Viaje viajeDuplicado = dadoQueNoExisteUnViaje();
        Conductor conductor = dadoQueExisteUnConductor();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.duplicarViajeDescartado(viajeDuplicado, conductor));

        // Validación
        assertThat("Los datos son nulos", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queNoSePuedaDuplicarUnViajeDescartadoSiElConductorEsNulo() {
        // Preparación
        Viaje viajeDuplicado = dadoQueExisteUnViaje();
        Conductor conductor = dadoQueNoExisteUnConductor();

        // Ejecución
        ClienteNoEncontradoException excepcion = assertThrows(ClienteNoEncontradoException.class, () -> viajeServicio.duplicarViajeDescartado(viajeDuplicado, conductor));

        // Validación
        assertThat("No esta logueado", equalTo(excepcion.getMessage()));
    }

   @Test
   public void queNoSePuedaDuplicarUnViaje() throws ViajeNoEncontradoException {
        // Preparación
        Viaje viajeDuplicado = dadoQueExisteUnViaje();
        doNothing().when(viajeRepositorio).editar(viajeDuplicado);

        // Ejecución
        viajeServicio.noDuplicarViaje(viajeDuplicado);

        // Validación
        assertFalse(viajeDuplicado.getEnviadoNuevamente());
   }

    @Test
    public void queNoSePuedaDuplicarUnViajeSiElViajePorParametroEsNulo() {
        // Preparación
        Viaje viajeDuplicado = dadoQueNoExisteUnViaje();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.noDuplicarViaje(viajeDuplicado));

        // Validación
        assertThat("Los datos son nulos", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queSePuedaActualizarViajeUnCancelado() throws ViajeNoEncontradoException {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        doNothing().when(viajeRepositorio).editar(viaje);

        // Ejecución
        viajeServicio.actualizarViajeCancelado(viaje);

        // Validación
        assertTrue(viaje.getEnviadoNuevamente());
    }

    @Test
    public void queNoSePuedaActualizarViajeUnCanceladoSiElViajeObtenidoEsNulo() {
        // Preparación
        Viaje viaje = dadoQueNoExisteUnViaje();

        // Ejecución
        ViajeNoEncontradoException excepcion = assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.actualizarViajeCancelado(viaje));

        // Validación
        assertThat("Los datos son nulos", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queSePuedaObtenerElHistorialDeEnviosDeUnCliente() throws ClienteNoEncontradoException {
        // Preparación
        Integer idCliente = 1;
        Cliente cliente = dadoQueExisteUnCliente();
        List<Viaje> historialDeViajes = dadoQueExisteUnHistorialDeViajesDelCliente(cliente);
        when(viajeRepositorio.obtenerViajesPorCliente(idCliente)).thenReturn(historialDeViajes);

        // Ejecución
        List<Viaje> historialDeViajesObtenidos = viajeServicio.obtenerHistorialDeEnvios(idCliente);

        // Validación
        assertThat(historialDeViajesObtenidos.size(), equalTo(2));
        assertThat(historialDeViajesObtenidos.get(0).getCliente(), equalTo(cliente));
        assertThat(historialDeViajesObtenidos.get(1).getCliente(), equalTo(cliente));
    }

    @Test
    public void queNoSePuedaObtenerElHistorialDeEnviosDeUnClienteSiElIdPorParametrosEsInvalido() {
        // Preparación
        Integer idCliente = dadoQueNoExisteElObjeto();

        // Ejecución
        ClienteNoEncontradoException excepcion = assertThrows(ClienteNoEncontradoException.class, () -> viajeServicio.obtenerHistorialDeEnvios(idCliente));

        // Validación
        assertThat("ID Invalido", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queNoSePuedaObtenerElHistorialDeEnviosDeUnClienteSiNoRealizoEnvios() throws ClienteNoEncontradoException {
        // Preparación
        Integer idCliente = 1;
        List<Viaje> historialDeEnvios = dadoQueExisteUnaListaDeViajesVacias();
        when(viajeRepositorio.obtenerViajesPorCliente(idCliente)).thenReturn(historialDeEnvios);

        // Ejecución
        List<Viaje> historialDeEnviosObtenidos = viajeServicio.obtenerHistorialDeEnvios(idCliente);

        // Validación
        assertThat(historialDeEnviosObtenidos.size(), equalTo(0));
        assertThat(historialDeEnviosObtenidos.size(), equalTo(historialDeEnvios.size()));
    }

    @Test
    public void queNoSePuedaFiltrarViajesPorDistanciaDelConductorConViajesCercanosSiElUsuarioEsNulo() {
        // Preparación
        Double latitudConductor = -34.603608;
        Double longitudConductor = -58.381732;
        Double distanciaAFiltrar = 10.0;
        Conductor conductor = dadoQueNoExisteUnConductor();

        // Ejecución
        UsuarioNoEncontradoException excepcion = assertThrows(UsuarioNoEncontradoException.class, () -> viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudConductor, longitudConductor, distanciaAFiltrar, conductor));

        // Validación
        assertThat("No esta logueado", equalTo(excepcion.getMessage()));
    }

    @Test
    public void queNoSePuedaFiltrarViajesPorDistanciaDelConductorConViajesCercanosSiLasCoordenadasSonNulas() {
        // Preparación
        Double latitudConductor = dadoQueNoExisteCoordenadas();
        Double longitudConductor = dadoQueNoExisteCoordenadas();
        Double distanciaAFiltrar = 10.0;
        Conductor conductor = dadoQueExisteUnConductor();

        // Ejecución
        CoordenadasNoEncontradasException excepcion = assertThrows(CoordenadasNoEncontradasException.class, () -> viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudConductor, longitudConductor, distanciaAFiltrar, conductor));
        // Validación
        assertThat("Coordenadas del usuario no encontradas", equalTo(excepcion.getMessage()));
    }

    private List<Viaje> dadoQueExistenViajesEnProcesosConUnConductorAsignado(Conductor conductor){
        List<Viaje> viajesConUnConductorAsignado = dadoQueExisteUnaListaDeViajesVacias();
        Cliente cliente = dadoQueExisteUnCliente();
        cliente.setNombre("Joaquin");
        Viaje viaje1 = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", cliente, 1000.0,  -34.667289, -58.530597, -34.663944, -58.536186, 1.2, TipoEstado.TERMINADO);
        Viaje viaje2 = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", cliente, 1000.0,  -34.668074, -58.534727, -34.665153, -58.541068, 1.2, TipoEstado.CANCELADO);
        Viaje viaje3 = new Viaje("Avend. Gral. San Martín 3339", "Marianooo Moreno 2842", cliente, 1000.0,  -34.670465, -58.533708, -34.668612, -58.529116, 1.2, null);
        Viaje viaje4 = new Viaje("San Justo", "Cañuelas", cliente, 1000.0,  -34.670889, -58.528515, -34.665206, -58.531884, 1.2, null);
        Viaje viaje5 = new Viaje("Marconi", "Villa 31", cliente, 1000.0,  -34.675865, -58.537184, -34.666441, -58.539866, 1.2, null);

        viaje1.setConductor(conductor);
        viaje2.setConductor(conductor);
        viaje3.setConductor(conductor);
        viaje4.setConductor(conductor);
        viaje5.setConductor(conductor);

        viajesConUnConductorAsignado.add(viaje1);
        viajesConUnConductorAsignado.add(viaje2);
        viajesConUnConductorAsignado.add(viaje3);
        viajesConUnConductorAsignado.add(viaje4);
        viajesConUnConductorAsignado.add(viaje5);
        return viajesConUnConductorAsignado;
    }

    private static Viaje dadoQueExisteUnViaje(){
        Cliente cliente = dadoQueExisteUnCliente();
        cliente.setNombre("Joaquin");
        Viaje viaje = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", cliente, 1000.0,  -34.667289, -58.530597, -34.663944, -58.536186, 1.2, null);
        viaje.setId(1);
        return viaje;
    }

    private List<Viaje> dadoQueExistenViajesCanceladosPorElCliente(Cliente cliente) {
        List<Viaje> viajesCanceladosPorCliente = dadoQueExisteUnaListaDeViajesVacias();
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();

        viaje1.setCliente(cliente);
        viaje1.setEstado(TipoEstado.CANCELADO);
        viaje2.setCliente(cliente);
        viaje2.setEstado(TipoEstado.CANCELADO);
        viaje3.setCliente(cliente);
        viaje3.setEstado(TipoEstado.CANCELADO);

        viajesCanceladosPorCliente.add(viaje1);
        viajesCanceladosPorCliente.add(viaje2);
        viajesCanceladosPorCliente.add(viaje3);

        return viajesCanceladosPorCliente;
    }

    private List<Viaje> dadoQueExistenViajesCreadosPorElCliente(Cliente cliente) {
        List<Viaje> viajesCreadosPorCliente = dadoQueExisteUnaListaDeViajesVacias();
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();

        viaje1.setCliente(cliente);
        viaje1.setEstado(TipoEstado.PENDIENTE);
        viaje2.setCliente(cliente);
        viaje2.setEstado(TipoEstado.PENDIENTE);
        viaje3.setCliente(cliente);
        viaje3.setEstado(TipoEstado.PENDIENTE);

        viajesCreadosPorCliente.add(viaje1);
        viajesCreadosPorCliente.add(viaje2);
        viajesCreadosPorCliente.add(viaje3);

        return viajesCreadosPorCliente;
    }

    private List<Viaje> dadoQueExisteUnHistorialDeViajesDelCliente(Cliente cliente) {
        List<Viaje> historialDeViajesDelCliente = dadoQueExisteUnaListaDeViajesVacias();
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();

        viaje1.setCliente(cliente);
        viaje1.setEstado(TipoEstado.TERMINADO);
        viaje1.setEnviadoNuevamente(false);
        viaje2.setCliente(cliente);
        viaje2.setEstado(TipoEstado.CANCELADO);
        viaje2.setEnviadoNuevamente(false);
        viaje3.setCliente(cliente);
        viaje3.setEstado(TipoEstado.PENDIENTE);
        viaje3.setEnviadoNuevamente(false);

        historialDeViajesDelCliente.add(viaje1);
        historialDeViajesDelCliente.add(viaje2);
        historialDeViajesDelCliente.add(viaje3);

        return historialDeViajesDelCliente;
    }

    private static Cliente dadoQueExisteUnCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        return cliente;
    }

    @Test
    public void queSePuedaObtenerUnViajeCreadoPorSuIdYQueMeDevuelvaElViaje() throws ViajeNoEncontradoException {

        Viaje viaje = new Viaje();

        Integer id = 1;

        when(viajeRepositorio.obtenerViajePorId(id)).thenReturn(viaje);

        Viaje viajeEncontrado = viajeServicio.obtenerViajePorId(id);

        assertThat(viajeEncontrado, equalTo(viaje));

    }

    @Test
    public void queNoSePuedaObtenerUnViajeCreadoPorSuIdYQueMeLanceLaExcepcionViajeNoEncontradoException() {

        Integer idInvalido = 0;

        when(viajeRepositorio.obtenerViajePorId(idInvalido)).thenReturn(null);

        assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.obtenerViajePorId(idInvalido));

    }

    @Test
    public void queUnViajeExistentePuedaSerEditado () throws ViajeNoEncontradoException {

        Viaje viaje = new Viaje();

        viajeServicio.actualizarViaje(viaje);

        verify(viajeRepositorio, times(1)).editar(viaje);
    }

    @Test
    public void queUnViajeNoPuedaSerEditadoPorqueEsNullNoSeEncuentraYSeLanceViajeNoEncontradoException () throws ViajeNoEncontradoException {

        assertThrows(ViajeNoEncontradoException.class, () -> viajeServicio.actualizarViaje(null));

    }

    @Test
    public void queSeCreeUnListadoDeViajesUnListadoDeViajesDescartadosYCadaViajeConUnPaqueteParaQueSePuedanCrearViajesDuplicadosYSeLosPuedaFiltrarCorrectamente() {

        List<Viaje> viajes = new ArrayList<>();

        List<Viaje> viajesDescartados = new ArrayList<>();

        Paquete paquete1 = new Paquete();

        paquete1.setId(1);

        Viaje viaje1 = new Viaje();

        viaje1.setPaquete(paquete1);

        viaje1.setDomicilioDeSalida("Salida1");

        viaje1.setDomicilioDeLlegada("Llegada1");

        Paquete paquete2 = new Paquete();

        paquete2.setId(2);

        Viaje viaje2 = new Viaje();

        viaje2.setPaquete(paquete2);

        viaje2.setDomicilioDeSalida("Salida2");

        viaje2.setDomicilioDeLlegada("Llegada2");

        Viaje viaje3 = new Viaje();

        viaje3.setPaquete(paquete1);

        viaje3.setDomicilioDeSalida("Salida1");

        viaje3.setDomicilioDeLlegada("Llegada1");


        viajes.add(viaje1);

        viajes.add(viaje2);

        viajesDescartados.add(viaje3);

        List<Viaje> result = viajeServicio.filtrarViajesDuplicados(viajes, viajesDescartados);

        assertEquals(1, result.size());
        assertEquals(viaje2, result.get(0));
    }

    private static Conductor dadoQueExisteUnConductor() {
        return new Conductor();
    }

    private static DatosViaje dadoQueExistenDatosViajes() {
        return new DatosViaje();
    }

    private static ArrayList<Viaje> dadoQueExisteUnaListaDeViajesVacias() {
        return new ArrayList<>();
    }

    private static Paquete dadoQueExisteUnPaquete() {
        return new Paquete();
    }

    private Integer dadoQueNoExisteElObjeto() {
        return null;
    }

    private Conductor dadoQueNoExisteUnConductor() {
        return null;
    }

    private DatosViaje dadoQueNoExisteDatosViaje() {
        return null;
    }

    private Viaje dadoQueNoExisteUnViaje() {
        return null;
    }

    private Cliente dadoQueNoExisteUnCliente() {
        return null;
    }

    private Paquete dadoQueNoExisteUnPaquete() {
        return null;
    }

    private Double dadoQueNoExisteCoordenadas() {
        return null;
    }
}