package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class RepositoryConductorTest {

    @Autowired
    private SessionFactory sessionFactory;

    private ConductorRepositorio conductorRepositorio;

    @BeforeEach
    public void init() {
        this.conductorRepositorio = new ConductorRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void registrarConductor() {
        // Given
        Conductor nuevoConductor = new Conductor("Juan", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        // When
        conductorRepositorio.registrar(nuevoConductor);

        // Then
        assertNotNull(nuevoConductor.getId());
    }

    @Test
    @Transactional

    public void buscarConductorExistente() {
        // Given
        Conductor nuevoConductor = new Conductor("Juan", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        sessionFactory.getCurrentSession().save(nuevoConductor);

        // When
        Conductor conductorEncontrado = conductorRepositorio.buscarConductor(nuevoConductor.getId());

        // Then
        assertNotNull(conductorEncontrado);
        assertEquals(nuevoConductor.getId(), conductorEncontrado.getId());
    }

    @Test
    @Transactional
    public void buscarConductorNoExistente() {
        // Given
        Integer idConductorInexistente = -1;

        // When
        Conductor conductorEncontrado = null;
        try {
            conductorEncontrado = conductorRepositorio.buscarConductor(idConductorInexistente);
        } catch (NoResultException e) {
            // La excepción NoResultException indica que no se encontró ningún conductor con el ID especificado.
            // En este caso, no hacemos nada ya que el test debe pasar si no se encuentra ningún conductor.
        }

        // Then
        assertNull(conductorEncontrado, "No se debe encontrar ningún conductor con el ID especificado");
    }

    @Test
    @Transactional

    public void editarConductorExistente() {
        // Given
        Conductor conductor = new Conductor("Juan", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        sessionFactory.getCurrentSession().save(conductor);

        // When
        conductor.setNombre("Pedro");
        conductorRepositorio.editarConductor(conductor);

        // Then
        Conductor conductorEditado = (Conductor) sessionFactory.getCurrentSession().get(Conductor.class, conductor.getId());
        assertEquals("Pedro", conductorEditado.getNombre());
    }

//    @Test
//    @Transactional
//    public void editarConductorNoExistente() {
//        // Given
//        Conductor conductorNoExistente = new Conductor();
//        conductorNoExistente.setId(-1); // ID de conductor inexistente
//
//        // When / Then
//        assertThrows(NoResultException.class, () -> conductorRepositorio.editarConductor(conductorNoExistente));
//    }
    @Test
    @Transactional

    public void buscarDuplicados() {
        // Given
        Conductor nuevoConductor = new Conductor("Juan", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        sessionFactory.getCurrentSession().save(nuevoConductor);

        // When
        Conductor conductorDuplicado = conductorRepositorio.buscarDuplicados("juan@example.com", "juanito");

        // Then
        assertNotNull(conductorDuplicado);
        assertEquals("juan@example.com", conductorDuplicado.getEmail());
        assertEquals("juanito", conductorDuplicado.getNombreUsuario());
    }
    @Test
    @Transactional

    public void borrarConductor() throws ConductorNoEncontradoException {
        // Given
        Conductor nuevoConductor = new Conductor("Juan", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        sessionFactory.getCurrentSession().save(nuevoConductor);

        // When
        conductorRepositorio.borrarConductor(nuevoConductor);

        // Then
        Conductor conductorBorrado = (Conductor) sessionFactory.getCurrentSession().get(Conductor.class, nuevoConductor.getId());
        assertNull(conductorBorrado);
    }

    @Test
    @Transactional

    public void borrarConductorNoExistente() {
        // Given
        Conductor conductorNoExistente = new Conductor();
        conductorNoExistente.setId(-1); // ID de conductor inexistente

        // When / Then
        assertDoesNotThrow(() -> conductorRepositorio.borrarConductor(conductorNoExistente));
    }
}