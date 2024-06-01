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


   /* @Test
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
    }*/

    /*@Test
    public void queSeEnvieLaSolicitudDeRegistroYQueSeEncuentrenDuplicadosPorEndeNoSeRegistre() throws ConductorDuplicadoException {
        Conductor conductor = new Conductor();
        conductor.setEmail("facu@gmail.com");
        conductor.setNombreUsuario("Facu");

        when(this.conductorRepositorio.buscarDuplicados(conductor.getEmail(), conductor.getNombreUsuario())).thenReturn(conductor);

        assertThrows(ConductorDuplicadoException.class, () -> conductorServicio.registrarConductorNoDuplicado(conductor));
    }*/

    @Test
    public void queSeObtengaConductorBuscadoPorIDSiExisteElMismo() throws ConductorNoEncontradoException {
        Integer conductorId = 1;
        Conductor conductorBuscado = new Conductor();
        conductorBuscado.setId(conductorId);

        when(conductorRepositorio.buscarConductorPorId(conductorId)).thenReturn(conductorBuscado);
        Conductor conductorObtenido = conductorServicio.obtenerConductorPorId(conductorId);

        assertNotNull(conductorObtenido);
        assertThat(conductorObtenido, equalTo(conductorBuscado));
    }

    @Test
    public void queSeNoEncuentreConductorBuscadoPorIDSiNoExiste() throws ConductorNoEncontradoException {
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
            conductorServicio.editarConductor(conductorEditado);
        });

        verify(conductorRepositorio).buscarConductorPorId(conductorEditado.getId());
        verify(conductorRepositorio, never()).editarConductor(any(Conductor.class));
    }

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
    public void queNoSePuedaRelacionarUnVehiculoAlConductor() throws ConductorNoEncontradoException {
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
    public void queSePuedaRelacionarUnVehiculoAlConductor() throws ConductorNoEncontradoException {
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
    public void queSePuedaBorrarUnConductorExistente() throws ConductorNoEncontradoException {
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



