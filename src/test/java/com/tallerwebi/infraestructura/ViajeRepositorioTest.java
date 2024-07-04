package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoEstado;
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

import java.util.List;

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
    public void queSePuedaObtenerLosViajesDelConductor(){
        // Preparación
        Integer totalDeViajesEsperados = 4;
        Conductor conductor = dadoQueExisteUnConductor();
        dadoQueExistenViajesConUnConductorAsignado(conductor);

        // Ejecución
        Integer totalDeViajesObtenidos = this.viajeRepositorio.obtenerViajesPorConductor(conductor).size();

        // Validación
        assertThat(totalDeViajesObtenidos, equalTo(totalDeViajesEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEditarUnViaje(){
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        Viaje viaje = dadoQueExisteUnViajeQueSeLeAsignaUnConductor(conductor);

        // Ejecución
        this.sessionFactory.getCurrentSession().save(viaje);

        // Validación
        assertThat(viaje.getConductor(), equalTo(conductor));
        assertThat(viaje.getDomicilioDeLlegada(), equalTo("Miami"));
        assertThat(viaje.getDomicilioDeSalida(), equalTo("Florida"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnViajePorSuId(){
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setId(1);
        this.sessionFactory.getCurrentSession().save(viaje);

        // Ejecución
        Viaje viajeObtenido = this.viajeRepositorio.obtenerViajePorId(viaje.getId());

        // Validación
        assertThat(viajeObtenido.getId(), equalTo(viaje.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanEncontrarLosViajesCercanos(){
        // Preparación
        Double latitudConductor = -34.665206;
        Double longitudConductor = -58.531884;
        Double distanciaAFiltrar = 100.0;
        Integer totalDeViajesEsperados = 2;
        dadoQueExistenViajes();

        // Ejecución
        List<Viaje> viajesCercanosObtenidos = this.viajeRepositorio.encontrarViajesCercanos(latitudConductor, longitudConductor, distanciaAFiltrar);

        // Validación
        assertThat(viajesCercanosObtenidos.size(), equalTo(totalDeViajesEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerTodosLosViajesPendientes() {
        // Preparación
        dadoQueExistenViajes();
        Integer viajesPendientesEsperados = 2;

        // Ejecución
        List<Viaje> viajesPendientesObtenidos = viajeRepositorio.traerTodosLosViajesPendientes();

        // Validación
        assertThat(viajesPendientesObtenidos.size(), equalTo(viajesPendientesEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerTodosLosViajesDescartadosPorElConductor() {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        dadoQueExistenViajesConUnConductorAsignado(conductor);
        Integer viajesDescartadosEsperados = 2;

        // Ejecución
        List<Viaje> viajesDescartadosObtenidos = viajeRepositorio.traerTodosLosViajesDescartadosPorConductor(conductor);

        // Validación
        assertThat(viajesDescartadosObtenidos.size(), equalTo(viajesDescartadosEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerTodosLosViajesDescartadosQueAfectanALaPenalizacionDelConductor() {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        dadoQueExistenViajesConUnConductorAsignado(conductor);
        Integer viajesDescartadosEsperados = 1;

        // Ejecución
        List<Viaje> viajesDescartadosObtenidos = viajeRepositorio.traerTodosLosViajesDescartadosQueAfectanPenalizacionPorConductor(conductor);

        // Validación
        assertThat(viajesDescartadosObtenidos.size(), equalTo(viajesDescartadosEsperados));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerTodosLosViajesCanceladosPorElConductor() {
        // Preparación
        Conductor conductor = dadoQueExisteUnConductor();
        dadoQueExistenViajesConUnConductorAsignado(conductor);
        Integer viajesCanceladosEsperados = 1;

        // Ejecución
        Integer viajesCanceladosObtenidos = viajeRepositorio.traerTodosLosViajesCanceladosPorConductor(conductor).size();

        // Validación
        assertThat(viajesCanceladosEsperados, equalTo(viajesCanceladosObtenidos));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnViaje() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setId(1);

        // Ejecución
        Viaje viajeObtenido = viajeRepositorio.guardarViaje(viaje);

        // Validación
        assertThat(viaje, equalTo(viajeObtenido));
        assertThat(viajeObtenido.getId(), equalTo(viaje.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerViajesPorElCliente() {
        // Preparación
        Cliente cliente = dadoQueExisteUnCliente();
        dadoQueExistenViajesConUnClienteAsignado(cliente);
        Integer viajesDelClienteEsperados = 4;

        // Ejecución
        Integer viajesDelClienteObtenido = viajeRepositorio.obtenerViajesPorCliente(cliente.getId()).size();

        // Validación
        assertThat(viajesDelClienteEsperados, equalTo(viajesDelClienteObtenido));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnViajeDuplicado() {
        // Preparación
        Viaje viaje = dadoQueExisteUnViaje();
        viaje.setId(1);

        // Ejecución
        Viaje viajeObtenido = viajeRepositorio.guardarViaje(viaje);

        // Validación
        assertThat(viaje, equalTo(viajeObtenido));
    }

    private void dadoQueExistenViajesConUnClienteAsignado(Cliente cliente) {
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();
        Viaje viaje4 = new Viaje();
        viaje1.setCliente(cliente);
        viaje2.setCliente(cliente);
        viaje3.setCliente(cliente);
        viaje4.setCliente(cliente);
        this.sessionFactory.getCurrentSession().save(viaje1);
        this.sessionFactory.getCurrentSession().save(viaje2);
        this.sessionFactory.getCurrentSession().save(viaje3);
        this.sessionFactory.getCurrentSession().save(viaje4);
    }

    private Cliente dadoQueExisteUnCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        this.sessionFactory.getCurrentSession().save(cliente);
        return cliente;
    }

    private void dadoQueExistenViajes() {
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();
        viaje1.setLatitudDeSalida(-34.667289);
        viaje1.setLongitudDeSalida(-58.530597);
        viaje1.setLatitudDeLlegada(-34.663944);
        viaje1.setLongitudDeLlegada(-58.536186);
        viaje1.setConductor(null);
        viaje1.setEstado(TipoEstado.PENDIENTE);
        viaje2.setLatitudDeSalida(-34.668074);
        viaje2.setLongitudDeSalida(-58.534727);
        viaje2.setLatitudDeLlegada(-34.665153);
        viaje2.setLongitudDeLlegada(-58.541068);
        viaje2.setConductor(null);
        viaje2.setEstado(TipoEstado.PENDIENTE);
        viaje3.setLatitudDeSalida(-34.670465);
        viaje3.setLongitudDeSalida(-58.533708);
        viaje3.setLatitudDeLlegada(-34.668612);
        viaje3.setLongitudDeLlegada(-58.529116);
        viaje3.setConductor(null);
        viaje3.setEstado(TipoEstado.CANCELADO);
        this.sessionFactory.getCurrentSession().save(viaje1);
        this.sessionFactory.getCurrentSession().save(viaje2);
        this.sessionFactory.getCurrentSession().save(viaje3);
    }

    private Conductor dadoQueExisteUnConductor() {
        Conductor conductor = new Conductor();
        this.sessionFactory.getCurrentSession().save(conductor);
        return conductor;
    }

    private void dadoQueExistenViajesConUnConductorAsignado(Conductor conductor) {
        Viaje viaje1 = new Viaje();
        Viaje viaje2 = new Viaje();
        Viaje viaje3 = new Viaje();
        Viaje viaje4 = new Viaje();
        viaje1.setConductor(conductor);
        viaje2.setConductor(conductor);
        viaje3.setConductor(conductor);
        viaje4.setConductor(conductor);
        viaje1.setEstado(TipoEstado.DESCARTADO);
        viaje1.setAfectaPenalizacion(true);
        viaje2.setEstado(TipoEstado.DESCARTADO);
        viaje3.setEstado(TipoEstado.PENDIENTE);
        viaje4.setEstado(TipoEstado.CANCELADO);
        viaje4.setAfectaPenalizacion(true);
        this.sessionFactory.getCurrentSession().save(viaje1);
        this.sessionFactory.getCurrentSession().save(viaje2);
        this.sessionFactory.getCurrentSession().save(viaje3);
        this.sessionFactory.getCurrentSession().save(viaje4);
    }

    private static Viaje dadoQueExisteUnViajeQueSeLeAsignaUnConductor(Conductor conductor) {
        Viaje viaje = new Viaje();
        viaje.setConductor(conductor);
        viaje.setDomicilioDeLlegada("Miami");
        viaje.setDomicilioDeSalida("Florida");
        return viaje;
    }

    private static Viaje dadoQueExisteUnViaje(){
        return new Viaje();
    }
}