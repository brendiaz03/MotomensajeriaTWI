package com.tallerwebi.dominio;

import com.tallerwebi.dominio.conductor.*;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    public void queAlregistrarUnConductorTeDevuelvaElMismo() throws Exception {

        Conductor nuevoConductor = new Conductor();
        nuevoConductor.setEmail("facu@gmail.com");
        nuevoConductor.setNombreUsuario("facu");

        when(conductorRepositorio.buscarDuplicados(anyString(), anyString())).thenThrow(NoResultException.class);
        when(conductorRepositorio.guardar(any(Conductor.class))).thenReturn(nuevoConductor);

        Conductor conductorRegistrado = conductorServicio.registrarConductorNoDuplicado(nuevoConductor);

        assertThat(conductorRegistrado, equalTo(nuevoConductor));
        verify(conductorRepositorio).buscarDuplicados(nuevoConductor.getEmail(), nuevoConductor.getNombreUsuario());
        verify(conductorRepositorio).guardar(nuevoConductor);
    }
//    @Test
//    public void queVerifiqueQueSeRegistreSiNoEstaDuplicado() throws Exception {
//        Conductor nuevoConductor = new Conductor();
//        nuevoConductor.setEmail("test@example.com");
//        nuevoConductor.setNombreUsuario("testuser");
//
//        when(conductorRepositorio.buscarDuplicados(nuevoConductor.getEmail(), nuevoConductor.getNombreUsuario())).thenThrow(NoResultException.class);
//        when(conductorRepositorio.guardar(nuevoConductor)).thenReturn(nuevoConductor);
//
//        Conductor conductorRegistrado = conductorServicio.registrarConductorNoDuplicado(nuevoConductor);
//
//        assertThat(conductorRegistrado, equalTo(nuevoConductor));
//        verify(conductorRepositorio).buscarDuplicados(nuevoConductor.getEmail(), nuevoConductor.getNombreUsuario());
//        verify(conductorRepositorio).guardar(nuevoConductor);
//    }
@Test
public void queSeObtengaConductorPorID() throws Exception {
    // Arrange
    Integer conductorId = 1;
    Conductor conductorBuscado = new Conductor();
    conductorBuscado.setId(conductorId);

    when(conductorRepositorio.buscarConductorPorId(conductorId)).thenReturn(conductorBuscado);

    Conductor conductorObtenido = conductorServicio.obtenerConductorPorId(conductorId);

    assertThat(conductorObtenido, equalTo(conductorBuscado));
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
    @Test
    public void testBorrarConductor() {
        Integer idConductor = 1;
        Conductor conductorABorrar = new Conductor();
        conductorABorrar.setId(idConductor);

        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(conductorABorrar);
        conductorServicio.borrarConductor(idConductor);
        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(null);
        Conductor conductorEliminado = conductorRepositorio.buscarConductorPorId(idConductor);

        assertNull(conductorEliminado);
        verify(conductorRepositorio, times(1)).borrarConductor(conductorABorrar);
    }
    @Test
    public void queSeRelacionVehiculoAlConductor() throws ConductorNoEncontradoException {
        Integer idConductor = 1;
        Vehiculo vehiculo = new Vehiculo();

        Conductor conductor = new Conductor();
        when(conductorRepositorio.buscarConductorPorId(idConductor)).thenReturn(conductor);

        Boolean relacionado = conductorServicio.RelacionarVehiculoAConductor(idConductor, vehiculo);

        assertThat(relacionado, equalTo(true));

    }


    }



