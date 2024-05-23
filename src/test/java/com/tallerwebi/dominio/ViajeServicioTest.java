package com.tallerwebi.dominio;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViajeServicioTest {

    private ViajeServicio viajeServicio;
    private ViajeRepositorio viajeRepositorio;

    @BeforeEach
    public void init(){
        this.viajeRepositorio = mock(ViajeRepositorio.class);
        viajeServicio = new ViajeServicioImpl(viajeRepositorio);
    }

    @Test
    public void queSePuedanObtenerTodosLosViajesDeLaBaseDeDatos(){
        List<Viaje> viajesEsperados = new ArrayList<>();
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());

        when(this.viajeRepositorio.obtenerTodosLosViajesDeLaBaseDeDatos()).thenReturn(viajesEsperados);

        List<Viaje> viajesObtenidos = this.viajeServicio.obtenerTodosLosViajesDeLaBaseDeDatos();

        assertThat(viajesObtenidos.size(), equalTo(viajesEsperados.size()));
    }

    @Test
    public void queSePuedanObtenerTodosLosViajesAceptadosPorElConductor(){
        Integer idConductor = 1;
        List<Viaje> viajesEsperados = new ArrayList<>();
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());

        when(this.viajeRepositorio.obtenerLosViajesAceptadosPorElConductor(idConductor)).thenReturn(viajesEsperados);

        List<Viaje> viajesObtenidos = this.viajeServicio.obtenerLosViajesAceptadosPorElConductor(idConductor);

        assertThat(viajesEsperados.size(), equalTo(viajesObtenidos.size()));
    }

    @Test
    public void queSePuedanObtenerTodosLosViajesDisponiblesSinUnConductorAsignado(){
        List<Viaje> viajesEsperados = new ArrayList<>();
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());

        when(this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes()).thenReturn(viajesEsperados);

        List<Viaje> viajesObtenidos = this.viajeServicio.obtenerLasSolicitudesDeViajesPendientes();

        assertThat(viajesEsperados.size(), equalTo(viajesObtenidos.size()));
    }

    @Test
    public void queSePuedaActualizarUnViajeAceptadoPorUnConductor(){
        // Preparación
        Integer idConductor = 1;
        Viaje viajeEsperado = new Viaje(9, "New York", "Los Angeles");

        // Ejecución
        when(this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(viajeEsperado.getId(), idConductor)).thenReturn(viajeEsperado);
        Viaje viajeObtenido = this.viajeServicio.actualizarViajeConElIdDelConductorQueAceptoElViaje(viajeEsperado.getId(), idConductor);

        // Verificación
        assertThat(viajeObtenido.getId(), equalTo(viajeEsperado.getId()));
    }

    @Test
    public void queSePuedaActualizarUnViajeAceptadoYDespuesRechazadoPorElConductor(){
        Integer idConductor = 1;
        Viaje viajeEsperado = new Viaje(9, "Brasil", "Uruguay");

        when(this.viajeRepositorio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(viajeEsperado.getId(), idConductor)).thenReturn(viajeEsperado);
        Viaje viajeObtenido = this.viajeServicio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(viajeEsperado.getId(), idConductor);

        assertThat(viajeObtenido.getIdConductor(), equalTo(null)); // Validacion que el Id del Conductor se desetea del viaje
        assertThat(viajeEsperado.getId(), equalTo(9));
        assertThat(viajeObtenido.getId(), equalTo(9));
        assertThat(viajeEsperado.getDomicilioDeSalida(), equalTo("Brasil"));
        assertThat(viajeEsperado.getDomicilioDeLlegada(), equalTo("Uruguay"));
        assertThat(viajeObtenido.getDomicilioDeSalida(), equalTo("Brasil"));
        assertThat(viajeObtenido.getDomicilioDeLlegada(), equalTo("Uruguay"));
    }
}
