package com.tallerwebi.dominio;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioRepositorio;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.dominio.usuario.UsuarioServicioImpl;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServicioTest {

    private UsuarioServicio usuarioServicio;
    private UsuarioRepositorio usuarioRepositorio;
    private SessionFactory session;

    @BeforeEach
    public void init() {
        this.usuarioRepositorio = mock(UsuarioRepositorio.class);
        this.usuarioServicio = new UsuarioServicioImpl(usuarioRepositorio);
        this.session=mock(SessionFactory.class);
    }


    //REGISTRAR USUARIO

    @Test
    public void queSeBusqueDuplicadosYAlNoEncontrarloSeRegistreCorrectamenteUnUsuarioDeTipoConductor() throws UsuarioDuplicadoException {
        // Arrange
        DatosUsuario nuevoUsuario = mock(DatosUsuario.class);
        when(nuevoUsuario.getEmail()).thenReturn("facu@gmail.com");
        when(nuevoUsuario.getNombreUsuario()).thenReturn("facu");
        when(nuevoUsuario.getTipoUsuario()).thenReturn(TipoUsuario.Conductor);

        when(usuarioRepositorio.buscarDuplicados("facu@gmail.com", "facu")).thenReturn(null);

        Conductor conductor = mock(Conductor.class);
        when(nuevoUsuario.toConductor()).thenReturn(conductor);
        when(usuarioRepositorio.guardarUsuario(conductor)).thenReturn(conductor);

        // Act
        Usuario usuarioRegistrado = usuarioServicio.registrarUsuario(nuevoUsuario);

        // Assert
        assertNotNull(usuarioRegistrado);
        assertEquals(conductor, usuarioRegistrado);
        verify(usuarioRepositorio, times(1)).guardarUsuario(conductor);
    }

    @Test
    public void queSeBusqueDuplicadosYSeLanceExcepcionSiYaExisteElUsuario() {
        // Arrange
        DatosUsuario nuevoUsuario = mock(DatosUsuario.class);
        when(nuevoUsuario.getEmail()).thenReturn("facu@gmail.com");
        when(nuevoUsuario.getNombreUsuario()).thenReturn("facu");

        Usuario usuarioDuplicado = mock(Usuario.class);
        when(usuarioRepositorio.buscarDuplicados("facu@gmail.com", "facu")).thenReturn(usuarioDuplicado);

        // Act & Assert
        assertThrows(UsuarioDuplicadoException.class, () -> usuarioServicio.registrarUsuario(nuevoUsuario));
    }

    //ACTUALIZAR USUARIO

    @Test
    public void queSeActualiceCorrectamenteUnUsuarioDeTipoConductor() throws UsuarioNoEncontradoException {
        // Arrange
        DatosUsuario datosUsuario = mock(DatosUsuario.class);
        when(datosUsuario.getId()).thenReturn(1);
        when(datosUsuario.getTipoUsuario()).thenReturn(TipoUsuario.Conductor);

        Conductor conductorExistente = mock(Conductor.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(conductorExistente);

        Conductor conductorEditado = mock(Conductor.class);
        when(datosUsuario.toConductor()).thenReturn(conductorEditado);
        when(conductorEditado.getId()).thenReturn(1);

        // Act
        usuarioServicio.actualizarUsuario(datosUsuario, TipoUsuario.Conductor);

        // Assert
        verify(usuarioRepositorio, times(1)).editarUsuario(conductorEditado);
    }


    @Test
    public void queSeActualiceCorrectamenteUnUsuarioDeTipoCliente() throws UsuarioNoEncontradoException {
        // Arrange
        DatosUsuario datosUsuario = mock(DatosUsuario.class);
        when(datosUsuario.getId()).thenReturn(1);
        when(datosUsuario.getTipoUsuario()).thenReturn(TipoUsuario.Cliente);

        Cliente clienteExistente = mock(Cliente.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(clienteExistente);

        Cliente clienteEditado = mock(Cliente.class);
        when(datosUsuario.toCliente()).thenReturn(clienteEditado);
        when(clienteEditado.getId()).thenReturn(1);

        // Act
        usuarioServicio.actualizarUsuario(datosUsuario, TipoUsuario.Cliente);

        // Assert
        verify(usuarioRepositorio, times(1)).editarUsuario(clienteEditado);
    }


    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioAlActualizar() {
        // Arrange
        DatosUsuario datosUsuario = mock(DatosUsuario.class);
        when(datosUsuario.getId()).thenReturn(1);
        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        // Act & Assert
        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.actualizarUsuario(datosUsuario, TipoUsuario.Conductor));
    }

    //OBTENER USUARIO POR ID

    @Test
    public void queSeObtengaCorrectamenteUnUsuarioPorId() throws UsuarioNoEncontradoException {
        // Arrange
        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuarioExistente);

        // Act
        Usuario usuarioObtenido = usuarioServicio.obtenerUsuarioPorId(1);

        // Assert
        assertNotNull(usuarioObtenido);
        assertEquals(usuarioExistente, usuarioObtenido);
    }

    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioPorId() {
        // Arrange
        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        // Act & Assert
        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.obtenerUsuarioPorId(1));
    }

    //INGRESAR IMAGEN

    @Test
    public void queSeIngreseCorrectamenteUnaImagenDePerfil() throws UsuarioNoEncontradoException, IOException {
        // Arrange
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.getBytes()).thenReturn("imagen".getBytes());

        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuarioExistente);

        // Act
        usuarioServicio.ingresarImagen(imagenMock, 1);

        // Assert
        verify(usuarioExistente, times(1)).setImagenPerfil(Base64.getEncoder().encode("imagen".getBytes()));
        verify(usuarioRepositorio, times(1)).editarUsuario(usuarioExistente);
    }

    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioAlIngresarImagen() {
        // Arrange
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        // Act & Assert
        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.ingresarImagen(imagenMock, 1));
    }

    @Test
    public void queSeLanceRuntimeExceptionEnCasoDeIOExceptionAlIngresarImagen() throws IOException {
        // Arrange
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.getBytes()).thenThrow(new IOException());

        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuarioExistente);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioServicio.ingresarImagen(imagenMock, 1));
    }

}
