package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.imagen.Imagen;
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
    public void queSeEnvieLaSolicitudDeRegistroYQueNoSeEncuentrenDuplicadosPorEndeSeRegistreCorrectamente() throws ConductorDuplicadoException {

        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setEmail("facu@gmail.com");
        nuevoConductor.setNombreUsuario("facu");

        when(conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(), nuevoConductor.getNombreUsuario())).thenThrow(NoResultException.class);
        when(conductorRepositorio.guardar(nuevoConductor)).thenReturn(nuevoConductor);

        Conductor conductorRegistrado = conductorServicio.registrarConductorNoDuplicado(nuevoConductor);

        assertNotNull(conductorRegistrado);
        assertEquals(nuevoConductor.getNombreUsuario(), conductorRegistrado.getNombreUsuario());
        assertEquals(nuevoConductor.getEmail(), conductorRegistrado.getEmail());
    }
    @Test
    public void queSeEnvieLaSolicitudDeRegistroYQueSeEncuentrenDuplicadosPorEndeNoSeRegistre() throws ConductorDuplicadoException {
        Conductor conductor = new Conductor();
        conductor.setEmail("facu@gmail.com");
        conductor.setNombreUsuario("Facu");

        when(this.conductorRepositorio.buscarDuplicados(conductor.getEmail(), conductor.getNombreUsuario())).thenReturn(conductor);

        assertThrows(ConductorDuplicadoException.class, () -> conductorServicio.registrarConductorNoDuplicado(conductor));
    }
    @Test
    public void queSeObtengaConductorBuscadoPorID() throws ConductorNoEncontradoException {
    Integer conductorId = 1;
    Conductor conductorBuscado = new Conductor();
    conductorBuscado.setId(conductorId);

    when(conductorRepositorio.buscarConductorPorId(conductorId)).thenReturn(conductorBuscado);
    Conductor conductorObtenido = conductorServicio.obtenerConductorPorId(conductorId);

    assertNotNull(conductorObtenido);
    assertThat(conductorObtenido, equalTo(conductorBuscado));
    }

    @Test
    public void queSeNoEncuentreConductorBuscadoPorID() throws ConductorNoEncontradoException {
        Integer conductorId = 1;
        Conductor conductorBuscado = new Conductor();
        conductorBuscado.setId(conductorId);

        when(conductorRepositorio.buscarConductorPorId(conductorId)).thenThrow(NoResultException.class);

        assertThrows(ConductorNoEncontradoException.class, () -> conductorServicio.obtenerConductorPorId(conductorBuscado.getId()));
    }

    @Test
    public void queSeEditeConductorSinImagenDePerfilManteniendoLaExistente() throws ConductorNoEncontradoException {
        Conductor conductorEditado = new Conductor();
        conductorEditado.setId(1);
        conductorEditado.setNombre("Kira");
        conductorEditado.setVehiculo(new Vehiculo());
        conductorEditado.setImagenPerfil(null);

        Conductor conductorExistente = new Conductor();
        conductorExistente.setId(1);
        conductorExistente.setNombre("Facu");
        conductorExistente.setVehiculo(new Vehiculo());
        byte[] imagenExistente = "fotoVieja".getBytes();
        conductorExistente.setImagenPerfil(imagenExistente);

        when(conductorRepositorio.buscarConductorPorId(conductorEditado.getId())).thenReturn(conductorExistente);

        conductorServicio.editarConductor(conductorEditado);

        assertEquals("Kira", conductorEditado.getNombre());
        assertNotNull(conductorExistente.getVehiculo());
        assertEquals(imagenExistente, conductorEditado.getImagenPerfil());
        verify(conductorRepositorio).buscarConductorPorId(conductorEditado.getId());
        verify(conductorRepositorio).editarConductor(conductorEditado);
    }

    @Test
    public void queSeEditeConductorConImagenDePerfilNueva() throws ConductorNoEncontradoException {
        Conductor conductorEditado = new Conductor();
        conductorEditado.setId(1);
        conductorEditado.setNombre("Kira");
        conductorEditado.setVehiculo(new Vehiculo());
        conductorEditado.setImagenPerfil(null);
        byte[] imagenNueva = "fotoNueva".getBytes();
        conductorEditado.setImagenPerfil(imagenNueva);

        Conductor conductorExistente = new Conductor();
        conductorExistente.setId(1);
        conductorExistente.setNombre("Facu");
        conductorExistente.setVehiculo(new Vehiculo());
        byte[] imagenExistente = "fotoVieja".getBytes();
        conductorExistente.setImagenPerfil(imagenExistente);

        when(conductorRepositorio.buscarConductorPorId(conductorEditado.getId())).thenReturn(conductorExistente);

        conductorServicio.editarConductor(conductorEditado);

        assertEquals("Kira", conductorEditado.getNombre());
        assertNotNull(conductorExistente.getVehiculo());
        assertEquals(imagenNueva, conductorEditado.getImagenPerfil());
        verify(conductorRepositorio).buscarConductorPorId(conductorEditado.getId());
        verify(conductorRepositorio).editarConductor(conductorEditado);
    }

    @Test
    public void queSeBusqueUnConductorParaEditarloYNoSeEncuentre() throws ConductorNoEncontradoException {
        Conductor conductorEditado = new Conductor();
        conductorEditado.setId(1);
        conductorEditado.setNombre("Kira");
        conductorEditado.setVehiculo(new Vehiculo());
        conductorEditado.setImagenPerfil("fotoNueva".getBytes());

        when(conductorRepositorio.buscarConductorPorId(conductorEditado.getId())).thenThrow(new NoResultException());

        assertThrows(ConductorNoEncontradoException.class, () -> {
            conductorServicio.editarConductor(conductorEditado);});

        verify(conductorRepositorio).buscarConductorPorId(conductorEditado.getId());
        verify(conductorRepositorio, never()).editarConductor(any(Conductor.class));
    }


    @Test
    public void queSePuedaBorrarUnConductorExistente() throws ConductorNoEncontradoException {
        Integer idConductor = 1;
        Conductor conductorABorrar = new Conductor();
        conductorABorrar.setId(idConductor);

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(conductorABorrar);
        doNothing().when(conductorRepositorio).borrarConductor(conductorABorrar);

        conductorServicio.borrarConductor(idConductor);

        verify(conductorRepositorio, times(1)).buscarConductorPorId(idConductor);
        verify(conductorRepositorio, times(1)).borrarConductor(conductorABorrar);
    }

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



