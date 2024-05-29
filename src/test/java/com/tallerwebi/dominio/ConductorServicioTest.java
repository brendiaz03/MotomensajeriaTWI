package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.persistence.NoResultException;

import java.io.NotActiveException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ConductorServicioTest {
    private ConductorServicio conductorServicio;
    private ConductorRepositorio conductorRepositorio;

    private SessionFactory sessionFactory;

    @BeforeEach
    public void init() {
        this.conductorRepositorio = mock(ConductorRepositorio.class);
        this.conductorServicio = new ConductorServicioImpl(conductorRepositorio);
    }


    @Test
    public void queAlRegistrarUnConductorTeDevuelvaElMismo() throws Exception {

        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setEmail("facu@gmail.com");
        nuevoConductor.setNombreUsuario("facu");

        when(conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(), nuevoConductor.getNombreUsuario())).thenThrow(NoResultException.class);
        when(conductorRepositorio.guardar(nuevoConductor)).thenReturn(nuevoConductor);

        Conductor conductorRegistrado = conductorServicio.registrarConductorNoDuplicado(nuevoConductor);

        assertThat(conductorRegistrado, equalTo(nuevoConductor));
        assertThat(conductorRegistrado.getEmail(), equalTo("facu@gmail.com"));
    }
    @Test
    public void queNoRegistreConductorSiEstaDuplicado() throws Exception {
        Conductor conductor = new Conductor();
        conductor.setEmail("Facu");
        conductor.setNombreUsuario("Joaquin");

        when(this.conductorRepositorio.buscarDuplicados(conductor.getEmail(), conductor.getNombreUsuario())).thenReturn(conductor);

        assertThrows(ConductorDuplicadoException.class, () -> conductorServicio.registrarConductorNoDuplicado(conductor));
    }
    @Test
    public void queSeObtengaConductorPorID() throws Exception {
    Integer conductorId = 1;
    Conductor conductorBuscado = new Conductor();
    conductorBuscado.setId(conductorId);

    when(conductorRepositorio.buscarConductorPorId(conductorId)).thenReturn(conductorBuscado);
    Conductor conductorObtenido = conductorServicio.obtenerConductorPorId(conductorId);

    assertThat(conductorObtenido, equalTo(conductorBuscado));
    }

    @Test
    public void queSeNoEncuentreConductorPorID() throws Exception {
        Integer conductorId = 1;
        Conductor conductorBuscado = new Conductor();
        conductorBuscado.setId(conductorId);

        when(conductorRepositorio.buscarConductorPorId(conductorId)).thenThrow(NoResultException.class);

        assertThrows(ConductorNoEncontradoException.class, () -> conductorServicio.obtenerConductorPorId(conductorBuscado.getId()));
    }

    @Test
    public void queSeEditeConductor() throws ConductorNoEncontradoException {
        Conductor conductor = new Conductor();
        conductor.setNombre("Facu");

        Conductor conductorEnBD = new Conductor();
        conductorEnBD.setNombre("Facu");
        when(conductorRepositorio.buscarConductorPorId(conductor.getId())).thenReturn(conductorEnBD);

        conductor.setNombre("Kira");
        conductorServicio.editarConductor(conductor);

        assertThat(conductor.getNombre(), equalTo("Kira"));
    }

//    @Test
//    public void queSeEditeConductor2() throws ConductorNoEncontradoException {
//        // Arrange
//        Conductor conductorEditado = new Conductor(); // Set necessary properties
//        Conductor conductorEnBD = new Conductor(); // Set necessary properties
//        byte[] nuevaImagenBytes = "contenido_de_la_nueva_imagen".getBytes();
//        conductorEnBD.setImagenPerfil(nuevaImagenBytes);
//        when(conductorRepositorio.buscarConductorPorId(anyInt())).thenReturn(conductorEnBD);
//
//        // Act
//        conductorServicio.editarConductor(conductorEditado);
//
//        // Assert
//        verify(conductorRepositorio, times(1)).editarConductor(conductorEditado);
//        assertEquals(nuevaImagenBytes, conductorEditado.getImagenPerfil());
//    }

//    @Test
//    public void queSeEditeConductor3() throws ConductorNoEncontradoException {
//        // Arrange
//        Conductor conductorEditado = new Conductor(); // Set necessary properties
//        Conductor conductorEnBD = new Conductor(); // Set necessary properties
//        when(conductorRepositorio.obtenerConductorPorId(anyLong())).thenReturn(conductorEnBD);
//
//        // Act
//        conductorServicio.editarConductor(conductorEditado);
//
//        // Assert
//        verify(conductorRepositorio, times(1)).editarConductor(conductorEditado);
//    }
//    @Test
//    public void queSeEditeConductor4() throws ConductorNoEncontradoException {
//        // Arrange
//        Conductor conductorEditado = new Conductor(); // Set necessary properties including image
//        Conductor conductorEnBD = new Conductor(); // Set necessary properties
//        when(conductorRepositorio.obtenerConductorPorId(anyLong())).thenReturn(conductorEnBD);
//
//        // Act
//        conductorServicio.editarConductor(conductorEditado);
//
//        // Assert
//        verify(conductorRepositorio, times(1)).editarConductor(conductorEditado);
//    }

    @Test
    public void testBorrarConductor() {
        Integer idConductor = 1;
        Conductor conductorABorrar = new Conductor();
        conductorABorrar.setId(idConductor);

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(conductorABorrar);
        doNothing().when(conductorRepositorio).borrarConductor(conductorABorrar);

        conductorServicio.borrarConductor(idConductor);

        verify(conductorRepositorio, times(1)).buscarConductorPorId(idConductor);
        verify(conductorRepositorio, times(1)).borrarConductor(conductorABorrar);
    }

//    @Test
//    public void queSeIngreseUnaImagenPorFormulario()throws ConductorNoEncontradoException{
//            Integer idUsuario = 1; // Set the user id
//            Conductor conductor = new Conductor(); // Create a conductor object for image insertion
//            when(conductorRepositorio.buscarConductorPorId(idUsuario)).thenReturn(conductor);
//
//            doNothing().when(conductorRepositorio).editarConductor(conductor);
//    }

    @Test
    public void queSeRelacionVehiculoAlConductor() throws ConductorNoEncontradoException {
        Integer idConductor = 1;
        Vehiculo vehiculo = new Vehiculo();

        Conductor conductor = new Conductor();
        doNothing().when(conductorRepositorio).agregarVehiculoAConductor(idConductor,vehiculo);

        Boolean relacionado = conductorServicio.RelacionarVehiculoAConductor(idConductor, vehiculo);

        assertThat(relacionado, equalTo(true));
    }

    @Test
    public void queNoSeRelacionVehiculoAlConductor() throws ConductorNoEncontradoException {
        Integer idConductor = 1;
        Vehiculo vehiculo = new Vehiculo();

        Conductor conductor = new Conductor();
        doThrow(new NoResultException()).when(conductorRepositorio).agregarVehiculoAConductor(idConductor, vehiculo);

        Boolean relacionado = conductorServicio.RelacionarVehiculoAConductor(idConductor, vehiculo);

        assertThat(relacionado, equalTo(false));
    }


    }



