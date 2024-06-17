package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteServicio;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UsuarioControladorTest {

    private HttpSession session;
    private UsuarioControlador usuarioControlador;
    private UsuarioServicio usuarioServicio;
    private ClienteServicio clienteServicio;
    private ConductorServicio conductorServicio;



    @BeforeEach
    public void init(){
        this.session=mock(HttpSession.class);
        this.clienteServicio=mock(ClienteServicio.class);
        this.conductorServicio=mock(ConductorServicio.class);
        this.usuarioServicio = mock(UsuarioServicio.class);
        this.usuarioControlador = new UsuarioControlador(usuarioServicio,conductorServicio,clienteServicio);
    }

//MOSTRAR-FORM
    @Test
    public void queAlSolicitarRegistrarUsuarioSeMuestreElFormularioDeRegistro() throws UsuarioNoEncontradoException {
        String viewName="form-usuario";
        Usuario usuario= mock(Usuario.class);
        Boolean isEditForm= false;


        when(this.session.getAttribute("isEditForm")).thenReturn(false);
        ModelAndView mav = usuarioControlador.mostrarForm("", session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(DatosUsuario.class));
        assertThat((boolean) mav.getModel().get("isEditForm"), equalTo(false));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(false));
    }

//REGISTRAR-USUARIO
    @Test
    public void queAlSolicitarRegistrarUsuarioSeRegistreUnConductor() throws Exception {
        String redireccionConductor = "redirect:/form-vehiculo";
        DatosUsuario nuevo = mock(DatosUsuario.class);
        Conductor nuevoConductor = mock(Conductor.class);
        Integer idUsuario = 1;

        when(this.session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.session.getAttribute("isEditForm")).thenReturn(false);
        when(usuarioServicio.registrarUsuario(nuevo)).thenReturn(nuevoConductor);
        ModelAndView mav = usuarioControlador.registrarConductor(nuevo, session);

        assertThat(mav.getViewName(), equalToIgnoringCase(redireccionConductor));
        assertThat(session.getAttribute("IDUSUARIO"), equalTo(idUsuario));
        assertThat(session.getAttribute("isEditForm"), equalTo(false));
        verify(usuarioServicio, times(1)).registrarUsuario(nuevo);
        assertTrue(usuarioServicio.registrarUsuario(nuevo) instanceof Conductor);
    }

    @Test
    public void queAlSolicitarRegistrarUsuarioSeRegistreUnCliente() throws Exception {
        String redireccionCliente="redirect:/homeCliente";
        DatosUsuario nuevo = mock(DatosUsuario.class);
        Cliente nuevoCliente = mock(Cliente.class);
        Integer idUsuario = 1;

        when(this.session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.session.getAttribute("isEditForm")).thenReturn(false);
        when(usuarioServicio.registrarUsuario(nuevo)).thenReturn(nuevoCliente);
        ModelAndView mav = usuarioControlador.registrarConductor(nuevo, session);

        assertThat(mav.getViewName(), equalToIgnoringCase(redireccionCliente));
        assertThat(session.getAttribute("IDUSUARIO"), equalTo(idUsuario));
        assertThat(session.getAttribute("isEditForm"), equalTo(false));
        verify(usuarioServicio, times(1)).registrarUsuario(nuevo);
        assertTrue(usuarioServicio.registrarUsuario(nuevo) instanceof Cliente);
    }


//MOSTRAR-PERFIL
    @Test
    public void queAlSolicitarRedirigirseAlPerfilSeMuestreElPerfilDelUsuario() throws Exception {
        String viewName="perfil";
        Usuario usuario= mock(Usuario.class);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer)this.session.getAttribute("IDUSUARIO"))).thenReturn(usuario);
        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(Usuario.class));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(false));
    }

    @Test
    public void queAlSolicitarRedirigirseAlPerfilYNoSeEncuentreUsuarioSeMuestreUnMensajeDeError() throws UsuarioNoEncontradoException {
        String viewName="perfil";
        Usuario usuario= mock(Usuario.class);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer)this.session.getAttribute("IDUSUARIO"))).thenThrow(UsuarioNoEncontradoException.class);
        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(false));
    }
//MOSTRAR-EDITAR-FOTO-PERFIL
    @Test
    public void queAlSolicitarRedirigirseALaEdicionDeFotoDePerfilSeRendericeLaVistaDeEdicionDeFotoDePerfilDelUsuario() throws UsuarioNoEncontradoException {
        String viewName="foto-perfil";
        Usuario usuario= mock(Usuario.class);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer)this.session.getAttribute("IDUSUARIO"))).thenReturn(usuario);
        ModelAndView mav = usuarioControlador.irAEditarFotoPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(Usuario.class));
        assertThat(mav.getModel().get("usuario"), equalTo(usuario));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(true));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(false));
    }

    @Test
    public void queAlSolicitarRedirigirseALaEdicionDeFotoDePerfilYNoSeEncuentreUnUsuarioExistenteMuestreUnMensajeDeError() throws UsuarioNoEncontradoException {
        String viewName="foto-perfil";
        Usuario usuario= mock(Usuario.class);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer) this.session.getAttribute("IDUSUARIO"))).thenThrow(new UsuarioNoEncontradoException("No se encontro al usuario."));
        ModelAndView mav = usuarioControlador.irAEditarFotoPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(false));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().get("mensajeError"), equalTo("No se encontro al usuario."));
    }

//MOSTRAR-FORM-EDICION-USUARIO
    @Test
    public void queAlSolicitarEditarUsuarioSeMuestreElFormularioDeEdicion() throws UsuarioNoEncontradoException {
        String viewName="form-usuario";
        Usuario usuario= mock(Usuario.class);

        when(this.session.getAttribute("isEditForm")).thenReturn(true);
        when(this.usuarioServicio.obtenerUsuarioPorId((Integer)this.session.getAttribute("IDUSUARIO"))).thenReturn(usuario);

        ModelAndView mav = usuarioControlador.mostrarEditarFormulario(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(DatosUsuario.class));
        assertThat((boolean) mav.getModel().get("isEditForm"), equalTo(true));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(false));
    }

    @Test
    public void queAlSolicitarEditarUsuarioNoSeEncuentreElMismoYMUestreUnMensajeDeError() throws UsuarioNoEncontradoException {
        String viewName="form-usuario";
        Usuario usuario= mock(Usuario.class);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer)this.session.getAttribute("IDUSUARIO"))).thenThrow(UsuarioNoEncontradoException.class);

        ModelAndView mav = usuarioControlador.mostrarEditarFormulario(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(false));
    }

//EDITAR USUARIO
    @Test
    public void queAlSolicitarEditarUsuarioSeEdite() throws Exception {
        // Arrange
        String redireccionPerfil = "redirect:/perfil";
        DatosUsuario usuarioEditado = mock(DatosUsuario.class);
        TipoUsuario tipoUsuario = TipoUsuario.Conductor;
        Integer idUsuario = 1;

        // Simulamos la sesión
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("isEditForm")).thenReturn(false);

        // Act
        ModelAndView mav = usuarioControlador.editarConductor(session, usuarioEditado);

        // Assert
        assertThat(mav.getViewName(), equalToIgnoringCase(redireccionPerfil));
        assertThat(session.getAttribute("IDUSUARIO"), equalTo(idUsuario));
        assertThat(session.getAttribute("isEditForm"), equalTo(false));
        verify(usuarioServicio, times(1)).actualizarUsuario(usuarioEditado, tipoUsuario);
    }

//    @Test
//    public void queAlSolicitarEditarUsuarioYFalleLaEdicionSeMuestreElFormularioDeEdicion() throws Exception {
//        // Arrange
//        TipoUsuario tipoUsuario = TipoUsuario.Cliente;
//        String viewName = "form-usuario";
//        Integer idUsuario = 1;
//        DatosUsuario usuarioDto= mock(DatosUsuario.class);
//
//        // Simulamos la sesión
//        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
//        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
//
//        // Simulamos que falla la actualización del usuario
//        doThrow(UsuarioNoEncontradoException.class)
//                .when(usuarioServicio)
//                .actualizarUsuario(usuarioDto, tipoUsuario);
//
//        // Act
//        ModelAndView mav = usuarioControlador.editarConductor(session, new DatosUsuario());
//
//        // Assert
//        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
//        assertThat((boolean) mav.getModel().get("isEditForm"), equalTo(true));
//        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
//    }


//SUBIR-FOTO
    @Test
    public void queAlSubirUnaImagenDePerfilSeLeActualiceLaMismaAlUsuarioActual() throws UsuarioNoEncontradoException {
        String viewName="redirect:/perfil";
        Usuario usuario= mock(Usuario.class);
        MultipartFile imagen = mock(MultipartFile.class);

        ModelAndView mav = usuarioControlador.subirFoto(imagen,session);

        verify(usuarioServicio).ingresarImagen(imagen, (Integer) session.getAttribute("IDUSUARIO"));
        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
    }

//    @Test
//    public void queAlSubirUnaImagenDePerfilNoSeEncuentreAlUsuario() throws UsuarioNoEncontradoException {
//        String viewName="redirect:/foto-perfil";
//        Integer idUsuario = 1;
//        MultipartFile imagen = mock(MultipartFile.class);
//
//        doThrow(new UsuarioNoEncontradoException("")).when(usuarioServicio).ingresarImagen(imagen, idUsuario);
//
//        ModelAndView mav = this.usuarioControlador.subirFoto(imagen,session);
//
//        assertThat(mav.getViewName(),equalTo(viewName));
//        assertThat(mav.getModel().get("mensajeError"), equalTo("Conductor no encontrado"));
//        verify(usuarioServicio).ingresarImagen(imagen,idUsuario);
//    }
  }

