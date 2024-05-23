package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
    @Rollback
    public void queSePuedanObtenerTodosLosViajesPendientesSinUnConductorAsignado(){
        // Preparación
        dadoQueExistenViajes();
        Integer viajesEsperados = 3;

        // Ejecución
        Integer viajesObtenidos = this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes().size();

        // Validación
        assertThat(viajesObtenidos, equalTo(viajesEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerTodosLosViajesAceptadosPorElConductor(){
        // Preparación
        Integer totalDeViajesEsperados = 3;
        Conductor conductor = dadoQueExisteUnConductor();
        dadoQueExistenViajesConUnConductorAsginado(conductor);

        // Ejecución
        Integer totalDeViajesObtenidos = this.viajeRepositorio.obtenerViajesPorConductor(conductor).size();

        // Validación
        assertThat(totalDeViajesObtenidos, equalTo(totalDeViajesEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarUnViajeCuandoElConductorLoAcepta(){
        // Preparación
        Conductor conductor1 = dadoQueExisteUnConductor();

        Viaje viaje = dadoQueExisterUnViajeQueSeLeSeteaUnConductor(conductor1);

        this.sessionFactory.getCurrentSession().save(viaje);

        assertThat(viaje.getId(), equalTo(viaje.getId()));
        assertThat(viaje.getConductor().getId(), equalTo(conductor1.getId()));
        assertThat(viaje.getDomicilioDeLlegada(), equalTo("Miami"));
        assertThat(viaje.getDomicilioDeSalida(), equalTo("Florida"));
    }

    private static Viaje dadoQueExisterUnViajeQueSeLeSeteaUnConductor(Conductor conductor) {
        Viaje viaje = new Viaje();
        viaje.setConductor(conductor);
        viaje.setDomicilioDeLlegada("Miami");
        viaje.setDomicilioDeSalida("Florida");
        return viaje;
    }

    private void dadoQueExistenViajes() {
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();
        this.sessionFactory.getCurrentSession().save(viaje1);
        this.sessionFactory.getCurrentSession().save(viaje2);
        this.sessionFactory.getCurrentSession().save(viaje3);
    }

    private Conductor dadoQueExisteUnConductor() {
        Conductor conductor = new Conductor();
        conductor.setId(1);
        this.sessionFactory.getCurrentSession().save(conductor);
        return conductor;
    }

    private void dadoQueExistenViajesConUnConductorAsginado(Conductor conductor) {
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();
        viaje1.setConductor(conductor);
        viaje2.setConductor(conductor);
        viaje3.setConductor(conductor);
        this.sessionFactory.getCurrentSession().save(viaje1);
        this.sessionFactory.getCurrentSession().save(viaje2);
        this.sessionFactory.getCurrentSession().save(viaje3);
    }
}