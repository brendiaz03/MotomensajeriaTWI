package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.dominio.usuario.UsuarioRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ConductorRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private ConductorRepositorio conductorRepositorio;
    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void init() {
        this.conductorRepositorio = new ConductorRepositorioImpl(sessionFactory);
        this.usuarioRepositorio = new UsuarioRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeGuardeExitosamenteUnNuevoConductorConNombreEnLaBaseDeDatos() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setNombre("Facu");
        Conductor guardado=(Conductor)this.usuarioRepositorio.guardarUsuario(nuevoConductor);

        assertThat(guardado.getNombre(), equalTo("Facu"));
        assertNotNull(nuevoConductor.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeCreeUnConductorConNombreYLuegoSeBusqueSuExistenciaEnLaBaseDeDatos() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setNombre("Facu");
        this.sessionFactory.getCurrentSession().save(nuevoConductor);

        Conductor guardado = this.sessionFactory.getCurrentSession().get(Conductor.class, nuevoConductor.getId());

        assertThat(guardado.getNombre(), equalTo("Facu"));
        assertNotNull(nuevoConductor.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void queBusqueUnConductorNoExistenteEnLaBaseDeDatosYEnConsecuenciaLanceUnaExcepcion() {
        Integer idInexistente=-1;

        assertThrows(NoResultException.class, () -> {
            this.conductorRepositorio.buscarConductorPorId(idInexistente);
        });

    }

    @Test
    @Transactional
    @Rollback
    public void queSeCreeUnConductorConUnNombreYLuegoSeEditeCorrectamenteElNombreDelConductorExistente() {
        Conductor conductor = new Conductor();
        conductor.setNombre("facu");
        this.sessionFactory.getCurrentSession().save(conductor);
        conductor.setNombre("pepe");

        this.conductorRepositorio.editarConductor(conductor);
        Conductor conductorEditado = sessionFactory.getCurrentSession().get(Conductor.class, conductor.getId());

        assertThat(conductorEditado.getNombre(), equalTo("pepe"));
    }
}
