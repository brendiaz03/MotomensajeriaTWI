/*package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeRepository;
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
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class ViajeRepositoryTest {

    @Autowired
    private SessionFactory sessionFactory;
    private ViajeRepository viajeRepository;

    @BeforeEach
    public void init() {
        this.viajeRepository = new ViajeRepositoryImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerTodosLosViajesDeLaBaseDeDatos(){
        List<Viaje> viajesObtenidos = this.viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos();

        assertThat(viajesObtenidos.size(), equalTo(2));
        assertThat(viajesObtenidos.get(0).getPrecio(), equalTo("2500"));
        assertThat(viajesObtenidos.get(1).getPrecio(), equalTo("5000"));
    }
}*/
