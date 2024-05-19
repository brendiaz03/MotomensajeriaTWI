/*package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)

@Repository
public class RepositoryConductorTest {

    @Autowired
    private SessionFactory sessionFactory;
   private ConductorRepositorio conductorRepositorio;

    @BeforeEach
    public void init(){
        this.conductorRepositorio = new ConductorRepositorioImpl(sessionFactory);
    }
    @Test
    public void queAlBuscarDuplicadosNoEncuentreResultados() {
        // Arrange
        String email = "email@example.com";
        String nombreUsuario = "usuario";

        assertThrows(HibernateException.class, () -> {
            conductorRepositorio.buscarDuplicados(email, nombreUsuario);
        });
    }
//    @Test
//    @Transactional
//    public void queAlBuscarDuplicadosEncuentreResultados() {
//        // Arrange
//        String email = "facundo.varela00@gmail.com";
//        String nombreUsuario = "test@unlam.edu.ar";
//
//        Conductor nuevoConductor = new Conductor("Facundo", "Varela", 42952902,
//                "facundo.varela00@gmail.com", "test1234", "test@unlam.edu.ar",
//                "Pueyrredon 3339", "1561639242", "1234567891234567891234",null);
//
//        Conductor conductorDuplicado = conductorRepositorio.buscarDuplicados(email, nombreUsuario);
//
//        // Assert
//        assertThat(nuevoConductor.getNombre(), equalTo(conductorDuplicado.getNombre()));
//    }
//
//    @Test
//    @Transactional
//    public void queAlBuscarUnConductorPorIDDevuelvaUnConductor() {
//        // Arrange
//
//        Integer id=1;
//
//        Conductor nuevoConductor = new Conductor("Facundo", "Varela", 42952902,
//                "facundo.varela00@gmail.com", "test1234", "test@unlam.edu.ar",
//                "Pueyrredon 3339", "1561639242", "1234567891234567891234",null);
//
//        Conductor conductorDuplicado = conductorRepositorio.buscarConductor(id);
//
//        // Assert
//        assertThat(nuevoConductor.getNombre(), equalTo(conductorDuplicado.getNombre()));
//    }
//
//    @Test
//    @Transactional
//    public void queAlBuscarUnConductorPorIDDevuelvaUnHibernateException() {
//        // Arrange
//        Integer id=4;
//        // Assert
//            assertThrows(NoResultException.class, () -> {
//            conductorRepositorio.buscarConductor(id);
//        });
//    }
//
//    @Test
//    @Transactional
//    public void queSePuedaModificarUnConductor(){
//
//
//
//    }
//
//    @Test
//    @Transactional
//    public void queSePuedaEliminarUnConductor(){
//
//
//
//    }

}*/



