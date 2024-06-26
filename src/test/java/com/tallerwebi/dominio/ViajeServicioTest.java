package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.viaje.*;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ViajeServicioTest {

    private ViajeServicio viajeServicio;
    private ViajeRepositorio viajeRepositorio;

    @BeforeEach
    public void init() {
        this.viajeRepositorio = mock(ViajeRepositorio.class);
        this.viajeServicio = new ViajeServicioImpl(this.viajeRepositorio);
    }

    @Test
    public void queSePuedaObtenerUnViajeAceptadoPorSuId() {
        // Preparación
        Viaje viajeAceptado = dadoQueExisteUnViaje();
        viajeAceptado.setEstado(TipoEstado.PENDIENTE);
        when(this.viajeRepositorio.obtenerViajePorId(viajeAceptado.getId())).thenReturn(viajeAceptado);

        // Ejecución
        DatosViaje viajeObtenido = this.viajeServicio.obtenerViajeAceptadoPorId(viajeAceptado.getId());

        // Validación
        assertThat(viajeObtenido.getIdViaje(), equalTo(viajeAceptado.getId()));
    }

    @Test
    public void queSePuedanObtenerElHistorialDeViajesDelConductor() throws UsuarioNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        List<Viaje> viajes = dadoQueExistenViajesConUnConductorAsignado(conductor);
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // Ejecución
        List<DatosViaje> viajesObtenidos = viajeServicio.obtenerHistorialDeViajesConductor(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(2));
    }

    @Test
    public void queSePuedaEditarUnViaje() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setDomicilioDeSalida("Av. Gral. San Martín 3339");
        doNothing().when(viajeRepositorio).editar(viaje);

        // Ejecución
        viajeServicio.actualizarViaje(viaje);

        // Validación
        assertThat(viaje.getDomicilioDeSalida(), equalTo("Av. Gral. San Martín 3339"));
    }

    @Test
    public void queSePuedanObtenerLosViajesEnProceso(){
        // Preparación
        Conductor conductor = new Conductor();
        List<Viaje> viajesEnProceso = dadoQueExistenViajesConUnConductorAsignado(conductor);
        viajesEnProceso.get(0).setEstado(TipoEstado.TERMINADO);
        viajesEnProceso.get(0).setEstado(TipoEstado.CANCELADO);
        viajesEnProceso.get(0).setEstado(TipoEstado.DESCARTADO);
        viajesEnProceso.get(1).setEstado(TipoEstado.TERMINADO);
        viajesEnProceso.get(1).setEstado(TipoEstado.CANCELADO);
        viajesEnProceso.get(1).setEstado(TipoEstado.DESCARTADO);

        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajesEnProceso);

        // Ejecución
        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(3));
    }

    @Test
    public void queSePuedaDescartarUnViaje(){
        // Preparación
        Conductor conductor = new Conductor();
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setEstado(TipoEstado.DESCARTADO);
        viaje.setConductor(conductor);
        when(this.viajeRepositorio.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
        doNothing().when(viajeRepositorio).editar(viaje);

        // Ejecución
        this.viajeServicio.descartarViaje(viaje.getId(), conductor);

        //Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getId());
        assertThat(viaje.getEstado(), equalTo(TipoEstado.DESCARTADO));
        verify(viajeRepositorio).editar(viaje);
    }

    @Test
    public void queSePuedaCancelarUnViaje(){
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(1);
        when(viajeRepositorio.obtenerViajePorId(viaje.getIdViaje())).thenReturn(viajeEsperado);
        viajeEsperado.setEstado(TipoEstado.CANCELADO);
        viajeEsperado.setFecha(LocalDateTime.now());
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.cancelarViaje(viaje);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getIdViaje());
        assertThat(viajeEsperado.getEstado(), equalTo(TipoEstado.CANCELADO));
        verify(viajeRepositorio).editar(viajeEsperado);
    }

    @Test
    public void queSePuedaTerminarUnViaje(){
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        DatosViaje viaje = new DatosViaje();
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
    public void queSePuedaAceptarUnViaje(){
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        Conductor conductor = new Conductor();
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(1);
        when(viajeRepositorio.obtenerViajePorId(viaje.getIdViaje())).thenReturn(viajeEsperado);
        viajeEsperado.setEstado(TipoEstado.PENDIENTE);
        viajeEsperado.setConductor(conductor);
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.aceptarViaje(viaje, conductor);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getIdViaje());
        assertThat(viajeEsperado.getEstado(), equalTo(TipoEstado.PENDIENTE));
        verify(viajeRepositorio).editar(viajeEsperado);
    }

    @Test
    public void queSePuedaPenalizarAlConductorSiDescartaCincoViajes() {
        // Preparación
        Conductor conductor = new Conductor();
        List<Viaje> viajesObtenidos = dadoQueExistenViajesConUnConductorAsignado(conductor);

        for (Viaje viaje : viajesObtenidos) {
            viaje.setEstado(TipoEstado.DESCARTADO);
        }

        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajesObtenidos);

        // Ejecución
        boolean isPenalizado = viajeServicio.estaPenalizado(conductor);

        // Validación
        assertThat(isPenalizado, equalTo(true));
    }

    @Test
    public void queSePuedaFiltrarViajesPorDistanciaDelConductorConViajesCercanos() {
        // Preparación
        Double latitudConductor = -34.603608;
        Double longitudConductor = -58.381732;
        Double distanciaAFiltrar = 10.0;
        Conductor conductor = new Conductor();
        List<Viaje> viajesCercanos = dadoQueExistenViajesConUnConductorAsignado(conductor);
        when(viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar)).thenReturn(viajesCercanos);

        // Ejecución
        List<DatosViaje> viajesFiltrados = viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudConductor, longitudConductor, distanciaAFiltrar, conductor);

        // Validación
        assertThat(viajesFiltrados.size(), equalTo(5));
    }

    private List<Viaje> dadoQueExistenViajesConUnConductorAsignado(Conductor conductor){
        List<Viaje> viajesConUnConductorAsignado = new ArrayList<>();
        Cliente cliente = new Cliente();
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
        Cliente cliente = new Cliente();
        cliente.setNombre("Joaquin");
        Viaje viaje = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", cliente, 1000.0,  -34.667289, -58.530597, -34.663944, -58.536186, 1.2, null);
        viaje.setId(1);
        return viaje;
    }
}