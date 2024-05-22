package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoRepositorio;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class VehiculoRepositorioTest {

    private VehiculoRepositorio vehiculoRepositorio;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void init(){

        this.vehiculoRepositorio = new VehiculoRepositorioImpl(sessionFactory);

    }

    @Test
    @Rollback
    @Transactional
    public void queAlBuscarElVehiculoPorPatenteDevuelvaElVehiculo(){

        Vehiculo vehiculoEsperado = new Vehiculo(1L, "L12345");

        Vehiculo vehiculoObtenido = this.vehiculoRepositorio.buscarVehiculoPorPatente(vehiculoEsperado.getPatente());

        assertThat(vehiculoEsperado.getId(), equalTo(vehiculoObtenido.getId()));
        assertThat(vehiculoEsperado.getPatente(), equalTo(vehiculoObtenido.getPatente()));

    }

    @Test
    @Rollback
    @Transactional
    public void queAlGuardarUnVehiculoMeDevuelvaElVehiculo(){

        Vehiculo vehiculoEsperado = new Vehiculo(1L, "L12345");

        Vehiculo vehiculoObtenido = this.vehiculoRepositorio.guardarVehiculo(vehiculoEsperado);

        assertThat(vehiculoEsperado, equalTo(vehiculoObtenido));
        assertThat(vehiculoEsperado.getId(), equalTo(vehiculoObtenido.getId()));
        assertThat(vehiculoEsperado.getPatente(), equalTo(vehiculoObtenido.getPatente()));
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEditarUnVehiculo(){

        Vehiculo vehiculoEsperado = new Vehiculo(1L, "L12345");

        Vehiculo vehiculoGuardado = vehiculoRepositorio.guardarVehiculo(vehiculoEsperado);

        vehiculoGuardado.setPatente("HolaMundo456");

        vehiculoRepositorio.editar(vehiculoGuardado);

        Vehiculo vehiculoObtenido = this.vehiculoRepositorio.buscarVehiculoPorPatente(vehiculoGuardado.getPatente());

        assertThat(vehiculoGuardado.getPatente(), equalTo(vehiculoObtenido.getPatente()));

    }

}
