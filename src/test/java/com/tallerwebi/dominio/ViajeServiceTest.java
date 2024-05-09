package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ViajeServiceImpl;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

public class ViajeServiceTest {

    private ViajeService viajeService;
    private ViajeRepository viajeRepository;

    @BeforeEach
    public void init() {
        this.viajeRepository = mock(ViajeRepository.class);
        this.viajeService = new ViajeServiceImpl(this.viajeRepository);
    }

    @Test
    public void queSePuedaObtenerUnViajePorSuId(){
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setId(1);
        mockearViajePorId(this.viajeRepository, viaje);

        // Ejecución
        Viaje viajeActual = dadoQueObtengoUnViajeDelServicePorId(viaje);

        // Validación
        assertThat(viajeActual.getId(), is(viaje.getId()));
    }

    @Test
    public void queSePuedaObtenerTodosLosViajes(){
        // Preparación
        List<Viaje> viajes = dadoQueExistenViajes();
        mockearViajes(this.viajeRepository, viajes);

        //Ejecución
        List<Viaje> viajesActuales = dadoQueObtengoTodosLosViajesDelService();

        //Verificación
        assertThat(viajesActuales.size(), is(viajes.size()));
    }

    @Test
    public void queSePuedaObtenerLosViajesDeUnClientePorSuId(){
        // Preparación
        List<Viaje> viajes = dadoQueExistenViajes();
        List<Viaje> viajesActuales = new ArrayList<>();
        Integer idCliente = 2;
        dadoQueObtengoLosViajesDelClientePorSuId(viajes, idCliente, viajesActuales);
        mockearViajesDelClientePorId(this.viajeRepository, viajesActuales, idCliente);

        // Ejecución
        List<Viaje> viajesActualesDelClienteObtenidosDelService = this.viajeService.obtenerViajesPorIdCliente(idCliente);

        //Validación
        assertThat(viajesActualesDelClienteObtenidosDelService.size(), is(3));
    }

    private static void dadoQueObtengoLosViajesDelClientePorSuId(List<Viaje> viajes, Integer IdCliente, List<Viaje> viajesActuales) {
        for (Viaje viaje : viajes) {
            if(viaje.getIdCliente() == IdCliente){
                viajesActuales.add(viaje);
            }
        }
    }

    private void mockearViajesDelClientePorId(ViajeRepository viajeRepository, List<Viaje> viajesActuales, Integer IdCliente) {
        when(viajeRepository.obtenerLosViajesDeUnCliente(IdCliente)).thenReturn(viajesActuales);
    }

    private List<Viaje> dadoQueObtengoTodosLosViajesDelService() {
        return this.viajeService.obtenerViajes();
    }

    private void mockearViajes(ViajeRepository viajeRepository, List<Viaje> viajes) {
        when(viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos()).thenReturn(viajes);
    }

    private List<Viaje> dadoQueExistenViajes() {
        List<Viaje> viajes = new ArrayList<>();
        Viaje viaje = new Viaje(1, 1, 1);
        Viaje viaje2 = new Viaje(1, 1, 2);
        Viaje viaje3 = new Viaje(2, 1, 3);
        Viaje viaje4 = new Viaje(2, 1, 4);
        Viaje viaje5 = new Viaje(2, 1, 5);

        viajes.add(viaje);
        viajes.add(viaje2);
        viajes.add(viaje3);
        viajes.add(viaje4);
        viajes.add(viaje5);

        return viajes;
    }

    private Viaje dadoQueExisteUnViaje() {
        List<Viaje> viajes = new ArrayList<>();
        Viaje viaje = new Viaje(1, 1, 1);

        viajes.add(viaje);
        return viaje;
    }

    private void mockearViajePorId(ViajeRepository viajeRepository, Viaje viajeActual) {
        when(viajeRepository.obtenerViajePorId(viajeActual.getId())).thenReturn(viajeActual);
    }

    private Viaje dadoQueObtengoUnViajeDelServicePorId(Viaje viaje) {
        return this.viajeService.obtenerViajePorId(viaje.getId());
    }







































    /*
    private List<Viaje> dadoQueExistenViajesRepetidosPorIdPaquete() {
        List<Viaje> viajes = new ArrayList<>();
        Viaje viaje = new Viaje(1, 1, 1);
        Viaje viaje2 = new Viaje(1, 1, 2);
        Viaje viaje3 = new Viaje(1, 1, 1);

        when(this.viajeRepository.guardarViaje(viaje)).thenReturn(true);
        when(this.viajeRepository.guardarViaje(viaje2)).thenReturn(true);
        when(this.viajeRepository.guardarViaje(viaje3)).thenReturn(false);

        return viajes;
    }
    @Test
    public void queSePuedaCrearYGuardarUnViaje() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();

        //Ejecución
        Boolean viajeGuardado = this.viajeService.guardarViaje(viaje);

        //Verificación
        assertThat(viajeGuardado, is(true));

    }


    @Test
    public void queNoSePuedaCrearUnViajeConUnPaqueteRepetido(){
        // Preparación
        Viaje viaje = new Viaje(1, 1, 1);
        Viaje viaje2 = new Viaje(1, 1, 2);
        Viaje viaje3 = new Viaje(1, 1, 1);

        when(this.viajeRepository.guardarViaje(viaje)).thenReturn(true);
        when(this.viajeRepository.guardarViaje(viaje2)).thenReturn(true);
        when(this.viajeRepository.guardarViaje(viaje3)).thenReturn(false);

        //Ejecución
        Boolean viajeGuardado = this.viajeService.guardarViaje(viaje);
        Boolean viajeGuardado2 = this.viajeService.guardarViaje(viaje2);
        Boolean viajeGuardado3 = this.viajeService.guardarViaje(viaje3);

        //Verificación
        assertThat(viajeGuardado, is(true));
        assertThat(viajeGuardado2, is(true));
        assertThat(viajeGuardado3, is(false));
    }
*/
}
