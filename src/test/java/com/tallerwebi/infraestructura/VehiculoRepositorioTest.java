package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.enums.ModeloVehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
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
    public void queAlBuscarElVehiculoPorPatenteDevuelvaElVehiculoYLoGuarde(){

        Vehiculo vehiculoEsperado= new Vehiculo("sfsfsfd", Color.ROJO, ModeloVehiculo.BMW, TipoVehiculo.AUTO, 0.0, 0.0);

        vehiculoRepositorio.guardarVehiculo(vehiculoEsperado);

        Vehiculo vehiculoObtenido = this.vehiculoRepositorio.buscarVehiculoPorPatente(vehiculoEsperado.getPatente());

        assertThat(vehiculoEsperado.getId(), equalTo(vehiculoObtenido.getId()));
        assertThat(vehiculoEsperado.getPatente(), equalTo(vehiculoObtenido.getPatente()));
        assertThat(vehiculoEsperado, equalTo(vehiculoObtenido));

    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEditarUnVehiculo(){

        Vehiculo vehiculoEsperado= new Vehiculo("sfsfsfd", Color.ROJO, ModeloVehiculo.BMW, TipoVehiculo.AUTO, 0.0, 0.0);

        vehiculoRepositorio.guardarVehiculo(vehiculoEsperado);

        vehiculoEsperado.setPatente("HolaMundo456");

        vehiculoRepositorio.editar(vehiculoEsperado);

        Vehiculo vehiculoObtenido = this.vehiculoRepositorio.buscarVehiculoPorPatente(vehiculoEsperado.getPatente());

        assertThat(vehiculoEsperado.getPatente(), equalTo(vehiculoObtenido.getPatente()));

    }

}