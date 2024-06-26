package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
import com.tallerwebi.dominio.paquete.PaqueteRepositorio;
import com.tallerwebi.dominio.viaje.Viaje;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void queSePuedaGuardarYObtenerUnPaquete() throws PaqueteNoEncontradoException {
        Paquete paquete = new Paquete(10.0, 5.0, true, "Descripción", "Facu");

        paqueteRepositorio.guardarPaquete(paquete);
        Paquete paqueteObtenido = paqueteRepositorio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteObtenido.getId(), equalTo(paquete.getId()));
        assertThat(paqueteObtenido, equalTo(paquete));
    }

    @Test
    @Transactional
    @Rollback
    public void queLanceExcepcionAlObtenerPaqueteNoExistente() {
        Integer paqueteID = 999;

        assertThrows(PaqueteNoEncontradoException.class, () -> {
            paqueteRepositorio.obtenerPaquetePorId(paqueteID);
        });
    }
}
