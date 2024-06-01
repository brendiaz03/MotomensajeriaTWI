package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.persistence.NoResultException;

import java.io.IOException;
import java.io.NotActiveException;
import java.util.Base64;

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


    /*@Test
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
    }*/
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
    public void queSePuedaIngresarUnaImagenAlPerfilDelConductor() throws IOException, ConductorNoEncontradoException {

        Conductor conductor = new Conductor();

        Integer conductorId = 29;

        conductor.setId(conductorId);

        when(this.conductorRepositorio.buscarConductorPorId(conductorId)).thenReturn(conductor);

        MockMultipartFile imagen = new MockMultipartFile("imagen", "imagen.jpg", "image/jpeg", "Este es el contenido de una imagen de prueba.".getBytes());

        byte[] imagenCodificada = Base64.getEncoder().encode(imagen.getBytes());

        conductorServicio.ingresarImagen(imagen, conductorId);

        assertThat(conductor.getImagenPerfil(), equalTo(imagenCodificada));
    }


    @Test
    public void queNoSePuedaRelacionarUnVehiculoAlConductor () throws ConductorNoEncontradoException {

        Conductor conductor = new Conductor();

        Vehiculo vehiculo = new Vehiculo();

        Long idVehiculo = 22L;

        Integer idConductor = 22;

        conductor.setId(idConductor);

        vehiculo.setId(idVehiculo);

        doThrow(new IllegalArgumentException("Conductor no encontrado con el ID: " + idConductor)).when(conductorRepositorio).agregarVehiculoAConductor(idConductor, vehiculo);

        Boolean resultado;

        try {
            resultado = conductorServicio.RelacionarVehiculoAConductor(idConductor, vehiculo);
        } catch (IllegalArgumentException e) {
            resultado = false;
        }

        assertThat(resultado, equalTo(false));

        assertThat(conductor.getVehiculo(), equalTo(null));
    }


    @Test
    public void queSePuedaRelacionarUnVehiculoAlConductor () throws ConductorNoEncontradoException {

        Conductor conductor = new Conductor();

        Vehiculo vehiculo = new Vehiculo();

        Long idVehiculo = 22L;

        Integer idConductor = 22;

        conductor.setId(idConductor);

        vehiculo.setId(idVehiculo);

        doNothing().when(conductorRepositorio).agregarVehiculoAConductor(idConductor, vehiculo);

        Boolean resultado;

        try {
            resultado = conductorServicio.RelacionarVehiculoAConductor(idConductor, vehiculo);
        } catch (ConductorNoEncontradoException e) {
            throw new RuntimeException(e);
        }

        conductor.setVehiculo(vehiculo);
        vehiculo.setConductor(conductor);

        assertThat(conductor.getVehiculo(), equalTo(vehiculo));
        assertThat(conductor.getId(), equalTo(vehiculo.getConductor().getId()));
        assertThat(resultado, equalTo(true));
    }

    @Test
    public void queSePuedaBorrarUnConductor() throws ConductorNoEncontradoException {

        Integer idConductor = 1;
        Conductor conductorABorrar = new Conductor();
        conductorABorrar.setId(idConductor);

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(conductorABorrar);
        doNothing().when(conductorRepositorio).borrarConductor(conductorABorrar);

        Conductor conductorEsperado = conductorRepositorio.buscarConductorPorId(idConductor);
        assertThat(conductorEsperado.getId(), equalTo(conductorABorrar.getId()));

        conductorServicio.borrarConductor(idConductor);

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(null);
        conductorEsperado = conductorRepositorio.buscarConductorPorId(idConductor);

        assertNull(conductorEsperado);
    }


    }



