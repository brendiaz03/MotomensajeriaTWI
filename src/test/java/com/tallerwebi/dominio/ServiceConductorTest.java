//package com.tallerwebi.dominio;
//
//import com.tallerwebi.dominio.conductor.*;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.mock.web.MockMultipartFile;
//
//import javax.persistence.NoResultException;
//
//import java.io.IOException;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import java.util.Base64;
//
//
//public class ServiceConductorTest {
//    private ConductorServicio conductorServicio;
//    private ConductorRepositorio conductorRepositorio;
//
//    private SessionFactory sessionFactory;
//
//    @BeforeEach
//    public void init() {
//        this.conductorRepositorio = mock(ConductorRepositorio.class);
//        this.conductorServicio = new ConductorServicioServicioImpl(conductorRepositorio);
//    }
//
//
//    @Test
//    public void siYoIngresoLosDatosCorrectosSeRegistraElConductorEnLaBD() throws Exception {
//        // Arrange
//        Conductor nuevoConductor = new Conductor("Jose", "Perez", 42952902, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
//        when(conductorRepositorio.buscarDuplicados(anyString(), anyString())).thenThrow(new NoResultException());
//
//        Boolean resultado = null;
//        try {
//            resultado = conductorServicio.verificarDatosDeRegistro(nuevoConductor);
//        } catch (ConductorDuplicadoException e) {
//            fail("No se esperaba una excepción de conductor duplicado");
//        }
//
//        // Assert
//        assertThat(resultado, equalTo(true));
//    }
//
//    @Test
//    public void queObtengaConductorPorIdExistente() throws ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Integer idConductor = 123; // Supongamos un ID de conductor existente
//        Conductor conductorEsperado = new Conductor();
//        conductorEsperado.setId(idConductor);
//        // Configuración del mock del repositorio
//        when(conductorRepositorio.buscarConductor(idConductor)).thenReturn(conductorEsperado);
//
//        // Llamada al método a probar
//        Conductor conductorObtenido = conductorServicio.obtenerConductorPorId(idConductor);
//
//        // Verificación de que el método del repositorio fue llamado una vez con el ID del conductor
//        verify(conductorRepositorio, times(1)).buscarConductor(idConductor);
//
//        // Verificación de que el conductor obtenido es igual al conductor esperado
//        assertThat(conductorObtenido, equalTo(conductorEsperado));
//    }
//
//    @Test
//    public void queLanceExcepcionCuandoConductorNoExiste() {
//        // Configuración de datos de prueba
//        Integer idConductor = 456; // Supongamos un ID de conductor inexistente
//        // Configuración del mock del repositorio para lanzar una NoResultException
//        when(conductorRepositorio.buscarConductor(idConductor)).thenThrow(new NoResultException());
//
//        // Verificación de que llamar al método con un ID de conductor inexistente lance una excepción de ConductorNoEncontradoException
//        assertThrows(ConductorNoEncontradoException.class, () -> {
//            conductorServicio.obtenerConductorPorId(idConductor);
//        });
//    }
//    @Test
//    public void queEditeConductorSinCambiarImagenPerfil() throws ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Conductor conductorEditado = new Conductor();
//        conductorEditado.setId(123); // Supongamos un ID de conductor válido
//
//        // Configuración del mock del repositorio
//        when(conductorRepositorio.buscarConductor(123)).thenReturn(conductorEditado);
//
//        // Llamada al método a probar
//        conductorServicio.editarConductor(conductorEditado);
//
//        // Verificación de que el método del repositorio fue llamado una vez con el conductor editado
//        verify(conductorRepositorio, times(1)).editarConductor(conductorEditado);
//    }
//
//    @Test
//    public void queConserveImagenPerfilDelConductorEnBD() throws ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Conductor conductorEditado = new Conductor();
//        conductorEditado.setId(123); // Supongamos un ID de conductor válido
//
//        Conductor conductorEnBD = new Conductor();
//        conductorEnBD.setId(123);
//        byte[] nuevaImagen = new byte[0];
//        conductorEditado.setImagenPerfil(nuevaImagen);// Supongamos que el conductor en la base de datos tiene una imagen de perfil
//
//        // Configuración del mock del repositorio
//        when(conductorRepositorio.buscarConductor(123)).thenReturn(conductorEnBD);
//
//        // Llamada al método a probar
//        conductorServicio.editarConductor(conductorEditado);
//
//        // Verificación de que el método del repositorio fue llamado una vez con el conductor editado, incluyendo la imagen de perfil conservada
//        verify(conductorRepositorio, times(1)).editarConductor(conductorEditado);
//        assertThat(conductorEditado.getImagenPerfil(), equalTo(nuevaImagen));
//    }
//
//    @Test
//    public void queEditeConductorConNuevaImagenPerfil() throws ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Conductor conductorEditado = new Conductor();
//        conductorEditado.setId(123); // Supongamos un ID de conductor válido
//        byte[] nuevaImagen = new byte[0];
//        conductorEditado.setImagenPerfil(nuevaImagen);
//
//        // Configuración del mock del repositorio
//        when(conductorRepositorio.buscarConductor(123)).thenReturn(conductorEditado);
//
//        // Llamada al método a probar
//        conductorServicio.editarConductor(conductorEditado);
//
//        // Verificación de que el método del repositorio fue llamado una vez con el conductor editado, incluyendo la nueva imagen de perfil
//        verify(conductorRepositorio, times(1)).editarConductor(conductorEditado);
//        assertThat(conductorEditado.getImagenPerfil(), equalTo(nuevaImagen));
//    }
//    @Test
//    public void queBorreConductorExistente() throws ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Integer idConductor = 123; // Supongamos un ID de conductor existente
//        Conductor conductorABorrar = new Conductor();
//        conductorABorrar.setId(idConductor);
//
//        // Configuración del mock del repositorio para devolver el conductor a borrar
//        when(conductorRepositorio.buscarConductor(idConductor)).thenReturn(conductorABorrar);
//
//        // Llamada al método a probar
//        conductorServicio.borrarConductor(idConductor);
//
//        // Verificación de que el método del repositorio fue llamado una vez con el conductor a borrar
//        verify(conductorRepositorio, times(1)).borrarConductor(conductorABorrar);
//    }
//
//    @Test
//    public void queNoBorreConductorInexistente() {
//        // Configuración de datos de prueba
//        Integer idConductor = 456; // Supongamos un ID de conductor inexistente
//
//        // Configuración del mock del repositorio para lanzar una NoResultException
//        when(conductorRepositorio.buscarConductor(idConductor)).thenThrow(new NoResultException());
//
//        // Llamada al método a probar y verificación de que no se lanzó ninguna excepción
//        assertDoesNotThrow(() -> {
//            conductorServicio.borrarConductor(idConductor);
//        });
//
//        // Verificación de que el método del repositorio no fue llamado
//        verify(conductorRepositorio, never()).borrarConductor(any());
//    }
//
////    @Test
////    public void queIngresarImagenEditeConductorExistente() throws IOException, ConductorNoEncontradoException {
////        // Configuración de datos de prueba
////        Integer idUsuario = 123; // Supongamos un ID de usuario existente
////        byte[] nuevaImagenBytes = "contenido_de_la_nueva_imagen".getBytes();
////        MockMultipartFile imagen = new MockMultipartFile("imagenPerfil", "nueva_imagen.jpg", "image/jpeg", nuevaImagenBytes);
////
////        // Mock del repositorio para devolver un conductor existente
////        Conductor conductorExistente = new Conductor();
////        conductorExistente.setId(idUsuario);
////        ConductorRepositorio conductorRepositorioMock = mock(ConductorRepositorio.class);
////        when(conductorRepositorioMock.buscarConductor(idUsuario)).thenReturn(conductorExistente);
////
////
////        // Llamada al método a probar
////        conductorServicio.ingresarImagen(imagen, idUsuario);
////
////        // Verificación de que se llamó al método editarConductor con el conductor actualizado
////        verify(conductorRepositorioMock, times(1)).editarConductor(conductorExistente);
////        // Verificación de que el contenido de la imagen se estableció correctamente en el conductor
////        assertThat(Base64.getDecoder().decode(conductorExistente.getImagenPerfil()), equalTo(nuevaImagenBytes));
////    }
//
//    @Test
//    public void queIngresarImagenNoHagaNadaConUsuarioInexistente() throws IOException, ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Integer idUsuario = 456; // Supongamos un ID de usuario inexistente
//        byte[] nuevaImagenBytes = "contenido_de_la_nueva_imagen".getBytes();
//        MockMultipartFile imagen = new MockMultipartFile("imagenPerfil", "nueva_imagen.jpg", "image/jpeg", nuevaImagenBytes);
//
//        // Mock del repositorio para devolver un resultado nulo (usuario inexistente)
//        ConductorRepositorio conductorRepositorioMock = mock(ConductorRepositorio.class);
//        when(conductorRepositorioMock.buscarConductor(idUsuario)).thenReturn(null);
//
//        // Llamada al método a probar
//        conductorServicio.ingresarImagen(imagen, idUsuario);
//
//        // Verificación de que no se llamó al método editarConductor
//        verify(conductorRepositorioMock, never()).editarConductor(any());
//    }
//}
//
//
