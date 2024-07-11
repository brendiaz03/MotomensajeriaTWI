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

import static org.hamcrest.MatcherAssert.assertThat;
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
    public void queSeBusqueUsuarioDuplicadoYAlNoEncontrarloSeRegistreCorrectamenteUnUsuarioDeTipoConductor() throws UsuarioDuplicadoException {

        DatosUsuario nuevoUsuario = mock(DatosUsuario.class);

        when(nuevoUsuario.getEmail()).thenReturn("facu@gmail.com");
        when(nuevoUsuario.getNombreUsuario()).thenReturn("facu");
        when(nuevoUsuario.getTipoUsuario()).thenReturn(TipoUsuario.conductor);

        // Simulamos que buscarDuplicados lanza una excepción NoResultException
        when(usuarioRepositorio.buscarDuplicados("facu@gmail.com", "facu")).thenThrow(new NoResultException());

        // Mock para el usuario que se va a guardar
        Conductor conductor = new Conductor();
        conductor.setEmail("facu@gmail.com");
        conductor.setNombreUsuario("facu");

        when(nuevoUsuario.toConductor()).thenReturn(conductor);
        when(usuarioRepositorio.guardarUsuario(conductor)).thenReturn(conductor);

        // Verificamos el registro del usuario
        Usuario usuarioRegistrado = usuarioServicio.registrarUsuario(nuevoUsuario);

        assertNotNull(usuarioRegistrado);
        assertEquals(conductor, usuarioRegistrado);
        verify(usuarioRepositorio, times(1)).guardarUsuario(conductor);
    }

    @Test
    public void queSeBusqueUsuarioDuplicadoYQueAlEncontrarloSeLanceLaExcepcionUsuarioDuplicadoException () throws UsuarioDuplicadoException {

        DatosUsuario usuario = mock(DatosUsuario.class);

        when(usuario.getEmail()).thenReturn("cami123@outlook.com");

        when(usuario.getNombreUsuario()).thenReturn("Camila");

        Usuario usuarioDuplicado = mock(Usuario.class);

        when(this.usuarioRepositorio.buscarDuplicados("cami123@outlook.com", "Camila")).thenReturn(usuarioDuplicado);

        assertThrows(UsuarioDuplicadoException.class, () -> {
            usuarioServicio.registrarUsuario(usuario);
        });

    }

    @Test
    public void queSeBusqueDuplicadosYAlNoEncontrarloSeRegistreCorrectamenteUnUsuarioDeTipoCliente() throws UsuarioDuplicadoException {

        DatosUsuario usuario = mock(DatosUsuario.class);

        when(usuario.getEmail()).thenReturn("cami123@outlook.com");

        when(usuario.getNombreUsuario()).thenReturn("Camila");

        when(usuario.getTipoUsuario()).thenReturn(TipoUsuario.cliente);

        when(this.usuarioRepositorio.buscarDuplicados("cami123@outlook.com", "Camila")).thenThrow(new NoResultException());

        Cliente cliente = new Cliente();
        cliente.setEmail("cami123@outlook.com");
        cliente.setNombreUsuario("Camila");

        when(usuario.toCliente()).thenReturn(cliente);
        when(usuarioRepositorio.guardarUsuario(cliente)).thenReturn(cliente);

        Usuario usuarioRegistrado = usuarioServicio.registrarUsuario(usuario);

        assertNotNull(usuarioRegistrado);
        assertEquals(cliente, usuarioRegistrado);
        verify(usuarioRepositorio, times(1)).guardarUsuario(cliente);
    }

    //ACTUALIZAR USUARIO

    @Test
    public void queSeActualiceCorrectamenteUnUsuarioDeTipoConductor() throws UsuarioNoEncontradoException {
        DatosUsuario datosUsuario = mock(DatosUsuario.class);
        when(datosUsuario.getId()).thenReturn(1);
        when(datosUsuario.getTipoUsuario()).thenReturn(TipoUsuario.conductor);

        Conductor conductorExistente = mock(Conductor.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(conductorExistente);

        Conductor conductorEditado = mock(Conductor.class);
        when(datosUsuario.toConductor()).thenReturn(conductorEditado);
        when(conductorEditado.getId()).thenReturn(1);

        usuarioServicio.actualizarUsuario(datosUsuario, TipoUsuario.conductor);

        verify(usuarioRepositorio, times(1)).editarUsuario(conductorEditado);
    }


    @Test
    public void queSeActualiceCorrectamenteUnUsuarioDeTipoCliente() throws UsuarioNoEncontradoException {
        DatosUsuario datosUsuario = mock(DatosUsuario.class);
        when(datosUsuario.getId()).thenReturn(1);
        when(datosUsuario.getTipoUsuario()).thenReturn(TipoUsuario.cliente);

        Cliente clienteExistente = mock(Cliente.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(clienteExistente);

        Cliente clienteEditado = mock(Cliente.class);
        when(datosUsuario.toCliente()).thenReturn(clienteEditado);
        when(clienteEditado.getId()).thenReturn(1);

        usuarioServicio.actualizarUsuario(datosUsuario, TipoUsuario.cliente);

        verify(usuarioRepositorio, times(1)).editarUsuario(clienteEditado);
    }


    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioAlActualizar() {
        DatosUsuario datosUsuario = mock(DatosUsuario.class);
        when(datosUsuario.getId()).thenReturn(1);
        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.actualizarUsuario(datosUsuario, TipoUsuario.conductor));
    }

    //OBTENER USUARIO POR ID

    @Test
    public void queSeObtengaCorrectamenteUnUsuarioPorId() throws UsuarioNoEncontradoException {
        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuarioExistente);

        Usuario usuarioObtenido = usuarioServicio.obtenerUsuarioPorId(1);

        assertNotNull(usuarioObtenido);
        assertEquals(usuarioExistente, usuarioObtenido);
    }

    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioPorId() throws UsuarioNoEncontradoException {

        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.obtenerUsuarioPorId(1));
    }


    //INGRESAR IMAGEN

    @Test
    public void queSeIngreseCorrectamenteUnaImagenDePerfil() throws UsuarioNoEncontradoException, IOException {
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.getBytes()).thenReturn("imagen".getBytes());

        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuarioExistente);

        usuarioServicio.ingresarImagen(imagenMock, 1);

        verify(usuarioExistente, times(1)).setImagenPerfil(Base64.getEncoder().encode("imagen".getBytes()));
        verify(usuarioRepositorio, times(1)).editarUsuario(usuarioExistente);
    }

    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioAlIngresarImagen() throws UsuarioNoEncontradoException{
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.ingresarImagen(imagenMock, 1));
    }

    @Test
    public void queSeLanceRuntimeExceptionEnCasoDeIOExceptionAlIngresarImagen() throws IOException {
        MultipartFile imagenMock = mock(MultipartFile.class);
        when(imagenMock.getBytes()).thenThrow(new IOException());

        Usuario usuarioExistente = mock(Usuario.class);
        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuarioExistente);

        assertThrows(RuntimeException.class, () -> usuarioServicio.ingresarImagen(imagenMock, 1));
    }

    @Test
    public void dadoQueExisteUnUsuarioQueQuiereCerrarSuCuentaQueSeActualiceComoEliminadoTrue() throws UsuarioNoEncontradoException {

        Usuario usuario = mock(Usuario.class);

        when(usuarioRepositorio.getUsuarioById(1)).thenReturn(usuario);

        usuarioServicio.borrarCuenta(1);

        verify(usuarioRepositorio, times(1)).editarUsuario(usuario);

    }

    @Test
    public void queSeLanceExcepcionSiNoSeEncuentraElUsuarioAlBorrarCuenta() throws UsuarioNoEncontradoException{

        when(usuarioRepositorio.getUsuarioById(1)).thenThrow(new NoResultException());

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioServicio.borrarCuenta(1));

    }

}
