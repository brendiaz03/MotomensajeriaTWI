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
    public void queSeBusqueUnConductorExistenteEnLaBaseDeDatos() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setId(1);
        nuevoConductor.setNombre("Facu");

        usuarioRepositorio.guardarUsuario(nuevoConductor);
        Conductor guardado = conductorRepositorio.buscarConductorPorId(nuevoConductor.getId());

        assertThat(guardado.getNombre(), equalTo("Facu"));
        assertNotNull(nuevoConductor.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeBusqueUnConductorNoExistenteEnLaBaseDeDatosYEnConsecuenciaLanceUnaExcepcion() {
        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setId(1);
        nuevoConductor.setNombre("Facu");

        usuarioRepositorio.guardarUsuario(nuevoConductor);

        assertThrows(NoResultException.class, () -> {
            conductorRepositorio.buscarConductorPorId(3); //ES UN TEMA DE ID DE HIBERNATE (SI PONGO ID 2 ME TOMA A LOS ID DE TEST ANTERIORES)
        });
        assertNotNull(nuevoConductor.getId());
    }/*
    @Test
    @Transactional
    @Rollback
    public void queSePuedaEditarUnConductorExistente() {
        Conductor conductor = new Conductor();
        conductor.setCvu("123");
        usuarioRepositorio.guardarUsuario(conductor);
        conductor.setCvu("456");

        this.conductorRepositorio.editarConductor(conductor);
        Conductor conductorEditado = sessionFactory.getCurrentSession().get(Conductor.class, conductor.getId());

        assertThat(conductorEditado.getCvu(), equalTo("456"));
    }*/
}