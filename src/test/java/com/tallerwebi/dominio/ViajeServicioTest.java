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
    public void queSePuedaObtenerLasSolicitudesDeViajesPendientes() {
        //PREPARACION

        List<Viaje> viajes = new ArrayList<>();

        Viaje viajePendiente = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", false, false);

        Viaje viajePendiente2 = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, false);

        viajes.add(viajePendiente);

        viajes.add(viajePendiente2);

        //EJECUCIÓN
        when(this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes()).thenReturn(viajes);

        List<Viaje> viajesObtenidos = this.viajeServicio.obtenerLasSolicitudesDeViajesPendientes();

        //VALIDACIÓN
        assertThat(viajesObtenidos.size(), equalTo(2));

    }

    @Test
    public void queSePuedaObtenerViajeAceptadoPorIdDeViaje() {
        // PREPARACIÓN
        List<Viaje> viajes = new ArrayList<>();

        Viaje viajePendiente = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", false, false);

        viajePendiente.setId(1);

        viajes.add(viajePendiente);

        // EJECUCIÓN
        when(this.viajeRepositorio.obtenerViajePorId(viajePendiente.getId())).thenReturn(viajePendiente);

        Viaje viajeObtenidoPorId = this.viajeServicio.obtenerViajeAceptadoPorId(viajePendiente.getId());

        // VALIDACIÓN
        assertThat(viajeObtenidoPorId.getId(), equalTo(viajePendiente.getId()));

    }

    @Test
    public void queSePuedaObtenerHistorialDeViajes() {
        // PREPARACIÓN
        List<Viaje> viajes = new ArrayList<>();
        Conductor conductor = new Conductor();

        Viaje viajeTerminado = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", true, false);
        Viaje viajeCancelado = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, true);
        Viaje viajePendiente = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, false);

        viajeTerminado.setConductor(conductor);
        viajeCancelado.setConductor(conductor);
        viajePendiente.setConductor(conductor);

        viajes.add(viajeTerminado);
        viajes.add(viajeCancelado);
        viajes.add(viajePendiente);

        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // EJECUCIÓN
        List<Viaje> viajesObtenidos = viajeServicio.obtenerHistorialDeViajes(conductor);

        // VALIDACIÓN
        assertThat(viajesObtenidos.size(), equalTo(2));

    }

    @Test
    public void queSePuedaEditarUnViaje() {
        // PREPARACIÓN

        Viaje viaje = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", true, false);

        viaje.setDomicilioDeSalida("Av. Gral. San Martín 3339");

        doNothing().when(viajeRepositorio).editar(viaje);

        //EJECUCIÓN
        viajeServicio.actualizarViaje(viaje);

        //VALIDACIÓN
        assertThat(viaje.getDomicilioDeSalida(), equalTo("Av. Gral. San Martín 3339"));

    }

    @Test
    public void queSeObtenganViajesEnProceso(){
        // PREPARACIÓN
        List<Viaje> viajes = new ArrayList<>();
        Conductor conductor = new Conductor();

        Viaje viajeTerminado = new Viaje("Acevedo 3000", "Sgto. Cabral 2815", -34.667289, -58.530597, -34.663944, -58.536186, "1704", "1000", "Efectivo", true, false);
        Viaje viajeCancelado = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, true);
        Viaje viajeEnProceso = new Viaje("Av. Gral. San Martín 3339", "Mariano Moreno 2842", -34.668074, -58.534727, -34.665153, -58.541068, "1704", "1200", "Tarjeta", false, false);

        viajeTerminado.setConductor(conductor);
        viajeCancelado.setConductor(conductor);
        viajeEnProceso.setConductor(conductor);

        viajes.add(viajeTerminado);
        viajes.add(viajeCancelado);
        viajes.add(viajeEnProceso);

        when(viajeRepositorio.obtenerViajesPorConductor(conductor)).thenReturn(viajes);

        // EJECUCIÓN
        List<Viaje> viajesObtenidos = viajeServicio.obtenerViajesEnProceso(conductor);

        // VALIDACIÓN
        assertThat(viajesObtenidos.size(), equalTo(1));
        assertThat(viajesObtenidos.get(0), equalTo(viajeEnProceso));

    }

}
