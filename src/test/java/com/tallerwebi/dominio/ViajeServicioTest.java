package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.dominio.viaje.ViajeServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void queSePuedanObtenerTodasLasSolicitudesDeViajesPendientes() {
        // Preparación
        List<Viaje> viajesPendientesObtenidos = dadoQueExistenViajesPendientes();
        when(this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes()).thenReturn(viajesPendientesObtenidos);

        // Ejecución
        List<Viaje> viajesObtenidos = this.viajeServicio.obtenerLasSolicitudesDeViajesPendientes();

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(viajesPendientesObtenidos.size()));
    }

    @Test
    public void queSePuedaObtenerUnViajeAceptadoPorSuId() {
        // Preparación
        Viaje viajeAceptado = dadoQueExisteUnViajeAceptado();
        List<Viaje> viajes = new ArrayList<>();
        viajes.add(viajeAceptado);
        when(this.viajeRepositorio.obtenerViajePorId(viajeAceptado.getId())).thenReturn(viajeAceptado);

        // Ejecución
        Viaje viajeObtenido = this.viajeServicio.obtenerViajeAceptadoPorId(viajeAceptado.getId());

        // Validación
        assertThat(viajeObtenido.getId(), equalTo(viajeAceptado.getId()));
    }

    @Test
    public void queSePuedanObtenerLosViajesTerminadosYCancelados() {
        // Preparación
        Conductor conductor = new Conductor();
        List<Viaje> viajes = dadoQueExistenViajesConUnConductorAsignado(conductor);
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // Ejecución
        List<Viaje> viajesObtenidos = viajeServicio.obtenerHistorialDeViajes(conductor);

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
        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajesEnProceso);

        // Ejecución
        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        // Validación
        assertThat(viajesObtenidos.size(), equalTo(1));
    }

    private List<Viaje> dadoQueExistenViajesPendientes() {
        List<Viaje> viajesPendientes = new ArrayList<>();
        Viaje viajePendiente = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", false, false);
        Viaje viajePendiente2 = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, false);
        viajesPendientes.add(viajePendiente);
        viajesPendientes.add(viajePendiente2);
        return viajesPendientes;
    }

    private Viaje dadoQueExisteUnViajeAceptado(){
        Viaje viajeAceptado = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", false, false);
        viajeAceptado.setId(1);
        return viajeAceptado;
    }

    private List<Viaje> dadoQueExistenViajesConUnConductorAsignado(Conductor conductor){
        List<Viaje> viajesConUnConductorAsignado = new ArrayList<>();
        Viaje viajeTerminado = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", true, false);
        Viaje viajeCancelado = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, true);
        Viaje viajeEnProceso = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, false);

        viajeTerminado.setConductor(conductor);
        viajeCancelado.setConductor(conductor);
        viajeEnProceso.setConductor(conductor);

        viajesConUnConductorAsignado.add(viajeTerminado);
        viajesConUnConductorAsignado.add(viajeCancelado);
        viajesConUnConductorAsignado.add(viajeEnProceso);
        return viajesConUnConductorAsignado;
    }

    private static Viaje dadoQueExisteUnViaje() {
        return new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", true, false);
    }
}
