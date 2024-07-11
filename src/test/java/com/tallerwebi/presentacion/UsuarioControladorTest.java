package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioDuplicadoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void init(){
        this.session=mock(HttpSession.class);
        this.usuarioServicio = mock(UsuarioServicio.class);
        this.usuarioControlador = new UsuarioControlador(usuarioServicio);
    }

//MOSTRAR-FORM
    @Test
    public void queAlSolicitarRegistrarUsuarioSeMuestreElFormularioDeRegistro() throws UsuarioNoEncontradoException {
        String viewName="form-usuario";
        Usuario usuario= mock(Usuario.class);
        DatosUsuario usuarioDTO= mock(DatosUsuario.class);

        when(this.session.getAttribute("isEditForm")).thenReturn(false);
        ModelAndView mav = usuarioControlador.mostrarForm(usuarioDTO,"", session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(DatosUsuario.class));
        assertThat((boolean) mav.getModel().get("isEditForm"), equalTo(false));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true)); //correct
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
        String redireccionCliente="redirect:/home?registroExitoso=true";
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

    @Test
    public void queAlRegistrarUsuarioDuplicadoSeMuestreElFormularioConMensajeDeError() throws Exception {

        String mensajeError = "Usuario o Email ya existentes";

        DatosUsuario nuevo = mock(DatosUsuario.class);

        when(usuarioServicio.registrarUsuario(nuevo)).thenThrow(new UsuarioDuplicadoException(mensajeError));

        ModelAndView mav = usuarioControlador.registrarConductor(nuevo, session);

        assertThat(mav.getViewName(), equalToIgnoringCase("form-usuario"));
        assertThat(mav.getModel().get("usuario"), equalTo(nuevo));
        assertThat(mav.getModel().get("mensajeError"), equalTo(mensajeError));

    }

//MOSTRAR-PERFIL

    @Test
    public void queAlSolicitarRedirigirseAlPerfilYNoSeEncuentreUsuarioSeMuestreUnMensajeDeError() throws UsuarioNoEncontradoException {

        String expectedRedirect = "redirect:/*";
        Integer idUsuario = 1;

        when(this.session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        when(this.usuarioServicio.obtenerUsuarioPorId(idUsuario)).thenThrow(new UsuarioNoEncontradoException("No se encontro al usuario."));

        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(expectedRedirect));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().get("mensajeError"), equalTo("No se encontro al usuario. Por favor, vuelva a intentarlo."));

    }

    @Test
    public void queAlSolicitarRedirigirseAlPerfilYNoSeEncuentreElUsuarioSeMuestreUnMensajeDeError() throws UsuarioNoEncontradoException {

        String expectedRedirectViewName = "redirect:/*";
        String expectedErrorMessage = "No se encontro al usuario.";

        when(this.session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(this.usuarioServicio.obtenerUsuarioPorId(1)).thenThrow(new UsuarioNoEncontradoException(expectedErrorMessage));

        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(expectedRedirectViewName));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().get("mensajeError"), equalTo(expectedErrorMessage + " Por favor, vuelva a intentarlo."));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(false));
    }

    @Test
    public void queAlSolicitarRedirigirseAlPerfilYElUsuarioSeaConductorConVehiculoSeMuestreElPerfilConNoVehiculoEnFalse() throws Exception {

        String viewName = "perfil";

        Conductor conductor = mock(Conductor.class);

        when(conductor.getTipoUsuario()).thenReturn(TipoUsuario.conductor);
        when(conductor.getVehiculo()).thenReturn(mock(Vehiculo.class));

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer) this.session.getAttribute("IDUSUARIO"))).thenReturn(conductor);

        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(Conductor.class));
        assertThat(mav.getModel().get("noVehiculo"), equalTo(false));

    }

    @Test
    public void queAlSolicitarRedirigirseAlPerfilYElUsuarioSeaConductorSinVehiculoSeMuestreElPerfilConNoVehiculoEnTrue() throws Exception {

        String viewName = "perfil";

        Conductor conductor = mock(Conductor.class);

        when(conductor.getTipoUsuario()).thenReturn(TipoUsuario.conductor);
        when(conductor.getVehiculo()).thenReturn(null);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer) this.session.getAttribute("IDUSUARIO"))).thenReturn(conductor);

        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat(mav.getModel().get("usuario"), instanceOf(Conductor.class));
        assertThat(mav.getModel().get("noVehiculo"), equalTo(true));

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

        String expectedErrorMessage = "No se encontro al usuario.";
        String expectedRedirectViewName = "redirect:/*";

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);

        when(usuarioServicio.obtenerUsuarioPorId(1)).thenThrow(new UsuarioNoEncontradoException(expectedErrorMessage));

        ModelAndView mav = usuarioControlador.irAEditarFotoPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(expectedRedirectViewName));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().get("mensajeError"), equalTo(expectedErrorMessage + " Por favor, vuelva a intentarlo."));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(false));
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
    public void queSeLanceUsuarioNoEncontradoExcepcionAlSolicitarEditarUsuario() throws UsuarioNoEncontradoException {

        Integer idUsuario = 1;

        String expectedErrorMessage = "No se encontro al usuario.";

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        when(usuarioServicio.obtenerUsuarioPorId(idUsuario)).thenThrow(new UsuarioNoEncontradoException(expectedErrorMessage));

        ModelAndView mav = usuarioControlador.mostrarEditarFormulario(session);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/*"));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().get("mensajeError"), equalTo(expectedErrorMessage + " Por favor, vuelva a intentarlo."));
        assertThat(mav.getModel().containsKey("usuario"), equalTo(false));
    }

    @Test
    public void queSeMuestreMensajeDeNoVehiculoCuandoElConductorNoTieneUnVehiculoGuardado() throws UsuarioNoEncontradoException {

        String viewName = "perfil";

        Conductor conductor = mock(Conductor.class);

        when(conductor.getVehiculo()).thenReturn(null); // Conductor sin vehículo
        when(conductor.getTipoUsuario()).thenReturn(TipoUsuario.conductor);

        when(this.usuarioServicio.obtenerUsuarioPorId((Integer)this.session.getAttribute("IDUSUARIO"))).thenReturn(conductor);

        ModelAndView mav = usuarioControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(viewName));
        assertThat((boolean) mav.getModel().get("noVehiculo"), equalTo(true));
    }

    //EDITAR USUARIO
    @Test
    public void queAlSolicitarEditarUsuarioSeEdite() throws Exception {
        String redireccionPerfil = "redirect:/perfil";
        DatosUsuario usuarioEditado = mock(DatosUsuario.class);
        TipoUsuario tipoUsuario = TipoUsuario.conductor;
        Integer idUsuario = 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("isEditForm")).thenReturn(false);

        ModelAndView mav = usuarioControlador.editarConductor(session, usuarioEditado);

        assertThat(mav.getViewName(), equalToIgnoringCase(redireccionPerfil));
        assertThat(session.getAttribute("IDUSUARIO"), equalTo(idUsuario));
        assertThat(session.getAttribute("isEditForm"), equalTo(false));
        verify(usuarioServicio, times(1)).actualizarUsuario(usuarioEditado, tipoUsuario);
    }

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

    //BORRAR CUENTA

    @Test
    public void queAlSolicitarBorrarCuentaDeUnUsuarioLaCuentaSeBorreCorrectamente() throws UsuarioNoEncontradoException {

        Integer idUsuario = 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        ModelAndView mav = usuarioControlador.borrarCuenta(session);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/cerrar-sesion"));
        verify(usuarioServicio, times(1)).borrarCuenta(idUsuario);
    }

    @Test
    public void queAlSolicitarBorrarCuentaDeUnUsuarioYNoEncontrarUnUsuarioSeMuestreMensajeDeError() throws UsuarioNoEncontradoException {
        Integer idUsuario = 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doThrow(new UsuarioNoEncontradoException("No se encontro al usuario.")).when(usuarioServicio).borrarCuenta(idUsuario);

        ModelAndView mav = usuarioControlador.borrarCuenta(session);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/*"));
        assertThat(mav.getModel().containsKey("mensajeError"), equalTo(true));
        assertThat(mav.getModel().get("mensajeError"), equalTo("No se encontro al usuario. Por favor, vuelva a intentarlo."));
    }

}

