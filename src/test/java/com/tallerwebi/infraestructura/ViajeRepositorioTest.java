package com.tallerwebi.infraestructura;

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

//    @Autowired
//    private SessionFactory sessionFactory;
//
//    private ViajeRepositorio viajeRepositorio;
//
//
//    @BeforeEach
//    public void init(){
//        this.viajeRepositorio = new ViajeRepositorioImpl(sessionFactory);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedanObtenerTodosLosViajesDeLaBaseDeDatos(){
//        // Preparación
//        Integer totalDeViajesEsperados = 3;
//        Viaje viaje1 = new Viaje();
//        Viaje viaje2 = new Viaje();
//        Viaje viaje3 = new Viaje();
//
//        this.sessionFactory.getCurrentSession().save(viaje1);
//        this.sessionFactory.getCurrentSession().save(viaje2);
//        this.sessionFactory.getCurrentSession().save(viaje3);
//
//        // Ejecución
//        Integer totalDeViajesObtenidos = this.viajeRepositorio.obtenerTodosLosViajesDeLaBaseDeDatos().size();
//
//        // Validación
//        assertThat(totalDeViajesObtenidos, equalTo(totalDeViajesEsperados));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedanObtenerTodosLosViajesPendientesSinUnConductorAsignado(){
//        // Preparación
//        Integer viajesEsperados = 3;
//        Viaje viaje1 = new Viaje();
//        Viaje viaje2 = new Viaje();
//        Viaje viaje3 = new Viaje();
//
//        this.sessionFactory.getCurrentSession().save(viaje1);
//        this.sessionFactory.getCurrentSession().save(viaje2);
//        this.sessionFactory.getCurrentSession().save(viaje3);
//
//        // Ejecución
//        Integer viajesObtenidos = this.viajeRepositorio.obtenerLasSolicitudesDeViajesPendientes().size();
//
//        // Validación
//        assertThat(viajesObtenidos, equalTo(viajesEsperados));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaObtenerTodosLosViajesAceptadosPorElConductor(){
//        // Preparación
//        Integer idConductor = 1;
//        Integer totalDeViajesEsperados = 3;
//
//        Viaje viaje1 = new Viaje();
//        Viaje viaje2 = new Viaje();
//        Viaje viaje3 = new Viaje();
//
//        viaje1.setIdConductor(idConductor);
//        viaje2.setIdConductor(idConductor);
//        viaje3.setIdConductor(idConductor);
//
//        this.sessionFactory.getCurrentSession().save(viaje1);
//        this.sessionFactory.getCurrentSession().save(viaje2);
//        this.sessionFactory.getCurrentSession().save(viaje3);
//
//        // Ejecución
//        Integer totalDeViajesObtenidos = this.viajeRepositorio.obtenerLosViajesAceptadosPorElConductor(idConductor).size();
//
//        // Validación
//        assertThat(totalDeViajesObtenidos, equalTo(totalDeViajesEsperados));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaActualizarUnViajeCuandoElConductorLoAcepta(){
//        // Preparación
//        Integer idConductor = 1;
//
//        Viaje viaje = new Viaje(1, "Miami", "Florida");
//
//        this.sessionFactory.getCurrentSession().save(viaje);
//
//        // Ejecución
//        Viaje viajeActualizado = this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(viaje.getId(), idConductor);
//
//        // Validación
//        assertThat(viaje.getId(), equalTo(viajeActualizado.getId()));
//        assertThat(viajeActualizado.getIdConductor(), equalTo(idConductor));
//        assertThat(viajeActualizado.getDomicilioDeSalida(), equalTo("Miami"));
//        assertThat(viajeActualizado.getDomicilioDeLlegada(), equalTo("Florida"));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaActualizarUnViajeCuandoElConductorLoAceptaYDespuesLoRechaza(){
//        // Preparación
//        Integer idConductor = 1;
//        Viaje viaje = new Viaje(1, "Miami", "Florida");
//
//        this.sessionFactory.getCurrentSession().save(viaje);
//
//
//        // Ejecución
//        Viaje viajeActualizadoAceptadoYDespuesRechazado = this.viajeRepositorio.actualizarViajeAceptadoPorElConductor(viaje.getId(), idConductor);
//
//        // Validación
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getId(), equalTo(viaje.getId()));
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getIdConductor(), equalTo(idConductor)); // Se setea el Id del conductor
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeSalida(), equalTo("Miami"));
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeLlegada(), equalTo("Florida"));
//
//        // Ejecución
//        viajeActualizadoAceptadoYDespuesRechazado = this.viajeRepositorio.actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(viaje.getId(), idConductor);
//
//        // Validación
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getId(), equalTo(viaje.getId()));
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getIdConductor(), equalTo(null)); // Es null porque se desetea el Id del Conductor
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeSalida(), equalTo("Miami"));
//        assertThat(viajeActualizadoAceptadoYDespuesRechazado.getDomicilioDeLlegada(), equalTo("Florida"));
//    }
}
