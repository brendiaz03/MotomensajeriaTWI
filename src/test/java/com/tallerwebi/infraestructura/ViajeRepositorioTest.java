package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ViajeRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private ViajeRepositorio viajeRepositorio;


    @BeforeEach
    public void init(){
        this.viajeRepositorio = new ViajeRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void queSePuedanObtenerTodosLosViajesDeLaBaseDeDatos(){
        // Preparación
        Integer totalDeViajesEsperados = 9;

        // Ejecución
        Integer totalDeViajesObtenidos = this.viajeRepositorio.obtenerTodosLosViajesDeLaBaseDeDatos().size();

        // Validación
        assertThat(totalDeViajesObtenidos, equalTo(totalDeViajesEsperados));
    }

    @Test
    @Transactional
    public void queSePuedanObtenerTodosLosViajesPendientesSinUnConductorAsignado(){
        // Preparación
        Integer viajesEsperados = 3;

        // Ejecución
        Integer viajesObtenidos = this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes().size();

        // Validación
        assertThat(viajesObtenidos, equalTo(viajesEsperados));
    }

    @Test
    @Transactional
    public void queSePuedaObtenerTodosLosViajesAceptadosPorElConductor(){
        // Preparación
        Integer idConductor = 1;
        Integer totalDeViajesEsperados = 6;

        // Ejecución
        Integer totalDeViajesObtenidos = this.viajeRepositorio.obtenerLosViajesAceptadosPorElConductor(idConductor).size();

        // Validación
        assertThat(totalDeViajesObtenidos, equalTo(totalDeViajesEsperados));
    }

    @Test
    @Transactional
    public void queSePuedaActualizarUnViajeCuandoElConductorLoAcepta(){
        // Preparación
        Integer idConductor = 1;
        Integer idViaje = 7;

        // Ejecución
        Viaje viajeActualizado = this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(idViaje, idConductor);

        // Validación
        assertThat(viajeActualizado.getId(), equalTo(idViaje));
        assertThat(viajeActualizado.getIdConductor(), equalTo(idConductor));
        assertThat(viajeActualizado.getDomicilioDeSalida(), equalTo("Miami"));
        assertThat(viajeActualizado.getDomicilioDeLlegada(), equalTo("Florida"));
    }

    @Test
    @Transactional
    public void queSePuedaActualizarUnViajeCuandoElConductorLoAceptaYDespuesLoRechaza(){
        // Preparación
        Integer idConductor = 1;
        Integer idViaje = 7;

        // Ejecución
        Viaje viajeActualizadoAceptadoYDespuesRechazado = this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(idViaje, idConductor);

        // Validación
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getId(), equalTo(idViaje));
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getIdConductor(), equalTo(idConductor)); // Se setea el Id del conductor
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeSalida(), equalTo("Miami"));
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeLlegada(), equalTo("Florida"));

        // Ejecución
        viajeActualizadoAceptadoYDespuesRechazado = this.viajeRepositorio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(idViaje, idConductor);

        // Validación
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getId(), equalTo(idViaje));
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getIdConductor(), equalTo(null)); // Es null porque se desetea el Id del Conductor
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeSalida(), equalTo("Miami"));
        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeLlegada(), equalTo("Florida"));
    }
}
