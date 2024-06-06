package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.enums.ModeloVehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteRepositorio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoRepositorio;
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
import static org.mockito.Mockito.verify;

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
    @Rollback
    @Transactional
    public void queSePuedaGuardarUnPaquete(){

        Paquete paquete = new Paquete(10.0, 5.0, true);

        paquete.setId(1);

        this.paqueteRepositorio.guardarPaquete(paquete);

        Paquete paqueteObtenido = this.paqueteRepositorio.obtenerPaquetePorId(1);

        assertThat(paquete.getId(), equalTo(paqueteObtenido.getId()));
        assertThat(paquete, equalTo(paqueteObtenido));

    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEditarUnPaquete(){

        Paquete paquete = new Paquete(10.0, 5.0, true);

        paquete.setId(1);

        paqueteRepositorio.guardarPaquete(paquete);

        paquete.setPeso(25.25);

        paqueteRepositorio.editarPaquete(paquete);

        Paquete paqueteObtenido = this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteObtenido.getPeso(), equalTo(25.25));
        assertThat(paqueteObtenido.getPeso(), equalTo(paquete.getPeso()));

    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaObtenerUnPaquetePorSuIdYMeDevuelvaElPaquete(){

        Paquete paquete = new Paquete(10.0, 5.0, true);

        paquete.setId(30);

        paqueteRepositorio.guardarPaquete(paquete);

        Paquete paqueteObtenido = this.paqueteRepositorio.obtenerPaquetePorId(paquete.getId());

        assertThat(paqueteObtenido.getId(), equalTo(paquete.getId()));

    }

}
