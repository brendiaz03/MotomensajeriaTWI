package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.dominio.viaje.ViajeServicioImpl;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        viajeAceptado.setAceptado(true);
        when(this.viajeRepositorio.obtenerViajePorId(viajeAceptado.getId())).thenReturn(viajeAceptado);

        // Ejecución
        DatosViaje viajeObtenido = this.viajeServicio.obtenerViajeAceptadoPorId(viajeAceptado.getId());

        // Validación
        assertThat(viajeObtenido.getIdViaje(), equalTo(viajeAceptado.getId()));
    }

    @Test
    public void queSePuedanObtenerElHistorialDeViajesDelConductor() throws ConductorNoEncontradoException {
        // Preparación
        Conductor conductor = new Conductor();
        List<Viaje> viajes = dadoQueExistenViajesConUnConductorAsignado(conductor);
        viajes.get(0).setTerminado(true);
        viajes.get(1).setCancelado(true);
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // Ejecución
        List<DatosViaje> viajesObtenidos = viajeServicio.obtenerHistorialDeViajes(conductor);

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
        viajesEnProceso.get(0).setTerminado(true);
        viajesEnProceso.get(0).setCancelado(true);
        viajesEnProceso.get(0).setDescartado(true);
        viajesEnProceso.get(1).setTerminado(true);
        viajesEnProceso.get(1).setCancelado(true);
        viajesEnProceso.get(1).setDescartado(true);

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
        viaje.setDescartado(true);
        viaje.setConductor(conductor);
        when(this.viajeRepositorio.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
        doNothing().when(viajeRepositorio).editar(viaje);

        // Ejecución
        this.viajeServicio.descartarViaje(viaje.getId(), conductor);

        //Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getId());
        assertEquals(true, viaje.getDescartado());
        verify(viajeRepositorio).editar(viaje);
    }

    @Test
    public void queSePuedaCancelarUnViaje(){
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(1);
        when(viajeRepositorio.obtenerViajePorId(viaje.getIdViaje())).thenReturn(viajeEsperado);
        viajeEsperado.setCancelado(true);
        viajeEsperado.setFecha(LocalDateTime.now());
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.cancelarViaje(viaje);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getIdViaje());
        assertEquals(true, viajeEsperado.getCancelado());
        verify(viajeRepositorio).editar(viajeEsperado);
    }

    @Test
    public void queSePuedaTerminarUnViaje(){
        // Preparación
        Viaje viajeEsperado = dadoQueExisteUnViaje();
        DatosViaje viaje = new DatosViaje();
        viaje.setIdViaje(1);
        when(viajeRepositorio.obtenerViajePorId(viaje.getIdViaje())).thenReturn(viajeEsperado);
        viajeEsperado.setTerminado(true);
        viajeEsperado.setFecha(LocalDateTime.now());
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.terminarViaje(viaje);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getIdViaje());
        assertEquals(true, viajeEsperado.getTerminado());
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
        viajeEsperado.setAceptado(true);
        viajeEsperado.setConductor(conductor);
        doNothing().when(viajeRepositorio).editar(viajeEsperado);

        // Ejecución
        this.viajeServicio.aceptarViaje(viaje, conductor);

        // Validación
        verify(viajeRepositorio).obtenerViajePorId(viaje.getIdViaje());
        assertEquals(true, viajeEsperado.getAceptado());
        verify(viajeRepositorio).editar(viajeEsperado);
    }

    @Test
    public void queSePuedaPenalizarAlConductorSiDescartaCincoViajes() {
        // Preparación
        Conductor conductor = new Conductor();
        List<Viaje> viajesObtenidos = dadoQueExistenViajesConUnConductorAsignado(conductor);

        for (Viaje viaje : viajesObtenidos) {
            viaje.setDescartado(true);
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
        List<DatosViaje> viajesFiltrados = viajeServicio.filtrarViajesPorDistanciaDelConductor(latitudConductor, longitudConductor, distanciaAFiltrar);

        // Validación
        assertThat(viajesFiltrados.size(), equalTo(5));
    }

    private List<Viaje> dadoQueExistenViajesConUnConductorAsignado(Conductor conductor){
        List<Viaje> viajesConUnConductorAsignado = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setNombre("Joaquin");
        Viaje viaje1 = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", cliente, "1000", "1763", -34.667289, -58.530597, -34.663944, -58.536186, 1.2, false, false, false, false);
        Viaje viaje2 = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", cliente, "1000", "1763", -34.668074, -58.534727, -34.665153, -58.541068, 1.2, false, false, false, false);
        Viaje viaje3 = new Viaje("Avend. Gral. San Martín 3339", "Marianooo Moreno 2842", cliente, "1000", "1763", -34.670465, -58.533708, -34.668612, -58.529116, 1.2, false, false, false, false);
        Viaje viaje4 = new Viaje("San Justo", "Cañuelas", cliente, "1000", "1763", -34.670889, -58.528515, -34.665206, -58.531884, 1.2, false, false, false, false);
        Viaje viaje5 = new Viaje("Marconi", "Villa 31", cliente, "1000", "1763", -34.675865, -58.537184, -34.666441, -58.539866, 1.2, false, false, false, false);

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
        Viaje viaje = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", cliente, "1000", "1763", -34.667289, -58.530597, -34.663944, -58.536186, 1.2, false, false, false, false);
        viaje.setId(1);
        return viaje;
    }
}