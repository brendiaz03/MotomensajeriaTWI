package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class PaqueteRepositorioTest {

    private PaqueteRepositorio paqueteRepositorio;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void init(){

        this.paqueteRepositorio = new PaqueteRepositorioImpl(sessionFactory);

    }

    @Test
    @Transactional
    @Rollback
    public void queSeCreeUnNuevoPaqueteYSeGuardeExitosamenteEnLaBaseDeDatos() {

        Paquete paquete = new Paquete(10.0, 5.0, true, "Descripción", "Facu");

        Paquete guardado = paqueteRepositorio.guardarPaquete(paquete);

        assertThat(guardado.getDescripcion(), equalTo(paquete.getDescripcion()));
        assertNotNull(guardado);

    }


    @Test
    @Transactional
    @Rollback
    public void queSeCreeUnNuevoPaqueteYAlBuscarloEnLaBaseDeDatosSeObtengaElMismoDeManeraExitosa() {

        Paquete paquete = new Paquete(10.0, 5.0, true, "Descripción", "Facu");

        this.sessionFactory.getCurrentSession().save(paquete);

        Paquete paqueteObtenido = this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteObtenido.getDescripcion(), equalTo(paquete.getDescripcion()));
        assertNotNull(paqueteObtenido);

    }

}
