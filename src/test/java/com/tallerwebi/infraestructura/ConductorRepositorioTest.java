package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ConductorRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private ConductorRepositorio conductorRepositorio;

    @BeforeEach
    public void init() {
        this.conductorRepositorio = new ConductorRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarConductorEnBD() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setNombre("Facu");

        Conductor guardado= conductorRepositorio.guardar(nuevoConductor);

        assertThat(guardado.getNombre(),equalTo("Facu"));
        assertNotNull(nuevoConductor.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarConductorPorId() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setId(1);
        nuevoConductor.setNombre("Facu");

        this.conductorRepositorio.guardar(nuevoConductor);
        Conductor conductorEncontrado = conductorRepositorio.buscarConductorPorId(nuevoConductor.getId());

        assertNotNull(conductorEncontrado);
        assertEquals(nuevoConductor.getId(), conductorEncontrado.getId());
        assertEquals(nuevoConductor.getNombre(), conductorEncontrado.getNombre());
        assertThat(conductorEncontrado.getNombre(), equalTo("Facu"));
    }


    @Test
    @Transactional
    @Rollback
    public void queSePuedaEditarConductorExistente() {
        Conductor conductor = new Conductor();
        conductor.setCvu("123");
        conductorRepositorio.guardar(conductor);

        conductor.setCvu("456");
        this.conductorRepositorio.editarConductor(conductor);

        Conductor conductorEditado = (Conductor) sessionFactory.getCurrentSession().get(Conductor.class, conductor.getId());

        assertThat(conductorEditado.getCvu(), equalTo("456"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeEncuentrenConductoresDuplicados() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setId(1);
        nuevoConductor.setNombreUsuario("Facu");
        nuevoConductor.setEmail("Facu@gmail.com");

        this.conductorRepositorio.guardar(nuevoConductor);

        Conductor conductorEncontrado = conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(),nuevoConductor.getNombreUsuario());

        assertNotNull(conductorEncontrado);
        assertEquals(nuevoConductor.getId(), conductorEncontrado.getId());
        assertEquals(nuevoConductor.getNombre(), conductorEncontrado.getNombre());
        assertThat(conductorEncontrado.getNombreUsuario(), equalTo("Facu"));
    }
    @Test
    @Transactional
    @Rollback
    public void testQueSePuedaBorrarUnConductor() throws ConductorNoEncontradoException {
        Conductor nuevoConductor = new Conductor();
        Conductor guardado=conductorRepositorio.guardar(nuevoConductor);
        assertNotNull(guardado);

        conductorRepositorio.borrarConductor(nuevoConductor);
        Conductor conductorBorrado = (Conductor) sessionFactory.getCurrentSession().get(Conductor.class, nuevoConductor.getId());
        assertNull(conductorBorrado);
    }

    @Test
    @Transactional
    @Rollback
    public void testQueSePuedaAgregarUnVehiculoAlConductor()  {
        Conductor nuevoConductor = new Conductor();
        Vehiculo nuevoVehiculo= new Vehiculo();
        nuevoConductor.setId(1);

        conductorRepositorio.guardar(nuevoConductor);
        nuevoConductor.setVehiculo(nuevoVehiculo);
        sessionFactory.getCurrentSession().saveOrUpdate(nuevoConductor);

        assertNotNull(nuevoConductor.getVehiculo());
        assertThat(nuevoConductor.getVehiculo(),equalTo(nuevoVehiculo));
    }
    }