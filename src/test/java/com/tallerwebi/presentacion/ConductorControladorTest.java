package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorDuplicadoException;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ConductorControladorTest {

   private ConductorControlador conductorControlador;
   private ConductorServicio conductorServicio;
   private ImagenServicio imagenServicio;

   private VehiculoServicio vehiculoServicio;
   private HttpSession session;


    @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       this.conductorServicio = mock(ConductorServicio.class);
       this.imagenServicio = mock(ImagenServicio.class);
       this.vehiculoServicio=mock(VehiculoServicio.class);
       this.session = mock(HttpSession.class);
       this.conductorControlador = new ConductorControlador(this.conductorServicio, this.imagenServicio, this.vehiculoServicio);

   }

    @Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor() throws ConductorNoEncontradoException {
        String nombreEsperado= "registro-conductor";
        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);
        when(this.session.getAttribute("isEditForm")).thenReturn(false);
        ModelAndView mav = conductorControlador.mostrarFormConductor("", session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), instanceOf(Conductor.class));
        assertThat((boolean) mav.getModel().get("isEditForm"), is(false));
        assertThat(mav.getModel().containsKey("mensajeError"), is(false));
    }

    @Test
    public void queAlSolicitarPantallaRegistroConMensajeDeErrorMuestreFormularioConError() throws ConductorNoEncontradoException {
        String nombreEsperado= "registro-conductor";
        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        String mensajeError="Error";

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);
        when(this.session.getAttribute("isEditForm")).thenReturn(false);

        ModelAndView mav = conductorControlador.mostrarFormConductor(mensajeError, session);
        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), instanceOf(Conductor.class));
        assertThat((boolean) mav.getModel().get("isEditForm"), is(false));
        assertThat(mav.getModel().containsKey("mensajeError"), is(true));
    }

    @Test
    public void queAlSolicitarPantallaRegistroConIsEditFormTrueMuestreFormularioRegistroParaEdicion() throws ConductorNoEncontradoException {
        String nombreEsperado = "registro-conductor";
        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        Conductor conductor = new Conductor();
        Integer idConductor = 1;

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);
        when(session.getAttribute("isEditForm")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(conductorServicio.obtenerConductorPorId(idConductor)).thenReturn(conductor);

        ModelAndView mav = conductorControlador.mostrarFormConductor(null, session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), is(conductor));
        assertThat((boolean) mav.getModel().get("isEditForm"), is(true));
        assertThat(mav.getModel().containsKey("mensajeError"), is(false));
    }

    @Test
    public void queAlSolicitarPantallaEdicionYNoEncontrarConductorMuestreError() throws ConductorNoEncontradoException {
        String nombreEsperado = "registro-conductor";
        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Imagen auto = new Imagen();
        Imagen fondo = new Imagen();
        Imagen botonPS = new Imagen();
        Conductor conductor = new Conductor();
        Integer idConductor = 1;

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(imagenServicio.getImagenByName("auto")).thenReturn(auto);
        when(imagenServicio.getImagenByName("fondo")).thenReturn(fondo);
        when(imagenServicio.getImagenByName("botonPS")).thenReturn(botonPS);
        when(session.getAttribute("isEditForm")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(conductorServicio.obtenerConductorPorId(idConductor)).thenThrow(new ConductorNoEncontradoException("Conductor no encontrado"));

        ModelAndView mav = conductorControlador.mostrarFormConductor("", session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), is("Conductor no encontrado"));
    }


    @Test
    public void queAlSolicitarLaPantallaIrAPerfilSeMuestreElPerfilDelConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "perfil-conductor";
        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Integer idConductor = 1;

        Conductor conductor = new Conductor();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        conductor.setVehiculo(vehiculo);

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(session.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(conductorServicio.obtenerConductorPorId(idConductor)).thenReturn(conductor);

        ModelAndView mav = this.conductorControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("isUsuarioLogueado"), is(true));
    }

    @Test
    public void queAlSolicitarLaPantallaIrAPerfilYNoEncontrarConductorMuestreError() throws ConductorNoEncontradoException {
        String nombreEsperado = "perfil-conductor";
        Imagen logo = new Imagen();
        Imagen user = new Imagen();
        Integer idConductor = 1;

        Conductor conductor = new Conductor();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        conductor.setVehiculo(vehiculo);

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(session.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(conductorServicio.obtenerConductorPorId(idConductor)).thenThrow(new ConductorNoEncontradoException("Conductor no encontrado"));

        ModelAndView mav = this.conductorControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), is("Conductor no encontrado"));
    }

    @Test
    public void queSeMuestreElFormularioDeEditarConductor() {
        String nombreEsperado = "redirect:/registro-conductor";

        ModelAndView mav= this.conductorControlador.mostrarEditarConductor(session);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        verify(session).setAttribute("isEditForm", true);
    }

    @Test
    public void queSeMuestreElFormularioDeEditarImagenDePerfilDeConductorSiExisteElConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "foto-perfil";
        Integer idConductor = 1;
        Conductor conductor=new Conductor();
        Imagen logo = new Imagen();
        Imagen user = new Imagen();

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(session.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(conductorServicio.obtenerConductorPorId(idConductor)).thenReturn(conductor);

        ModelAndView mav = conductorControlador.irAEditarFotoPerfil(session);
        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("isUsuarioLogueado"),equalTo(true));
        assertThat(mav.getModel().get("conductor"),instanceOf(Conductor.class));
    }

    @Test
    public void queNoSeMuestreElFormularioDeEditarImagenDePerfilDeConductorSiNoExisteElConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "foto-perfil";
        Integer idConductor = 1;
        Conductor conductor=new Conductor();
        Imagen logo = new Imagen();
        Imagen user = new Imagen();

        when(imagenServicio.getImagenByName("logo")).thenReturn(logo);
        when(imagenServicio.getImagenByName("user")).thenReturn(user);
        when(session.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
        when(conductorServicio.obtenerConductorPorId(idConductor)).thenThrow(new ConductorNoEncontradoException("Conductor no encontrado"));

        ModelAndView mav = conductorControlador.irAEditarFotoPerfil(session);
        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("isUsuarioLogueado"),equalTo(true));
        assertThat(mav.getModel().get("conductor"),equalTo(null));
        assertThat(mav.getModel().get("mensajeError"), is("Conductor no encontrado"));
    }

    @Test
    public void queSePuedaRegistrarUnNuevoConductor() throws ConductorDuplicadoException {
        Conductor nuevoConductor = new Conductor();
        Conductor conductor = new Conductor();
        conductor.setId(1);

        when(conductorServicio.registrarConductorNoDuplicado(nuevoConductor)).thenReturn(conductor);

        ModelAndView mav = conductorControlador.registrarConductor(nuevoConductor, session);

        assertThat(mav.getViewName(), equalTo("redirect:/vehiculo"));
        verify(session).setAttribute("IDUSUARIO", conductor.getId());
        verify(conductorServicio).registrarConductorNoDuplicado(nuevoConductor);
    }

    @Test
    public void queNoSePuedaRegistrarUnConductorDuplicado() throws ConductorDuplicadoException {
        Conductor nuevoConductor = new Conductor();
        Conductor conductor = new Conductor();
        conductor.setId(1);
        when(conductorServicio.registrarConductorNoDuplicado(nuevoConductor)).thenThrow(new ConductorDuplicadoException("Conductor Duplicado"));

        ModelAndView mav = conductorControlador.registrarConductor(nuevoConductor, session);

        assertThat(mav.getViewName(), equalTo("registro-conductor"));
        assertThat(mav.getModel().get("mensajeError"), equalTo("Conductor Duplicado"));
    }
    @Test
    public void queSePuedaEditarUnConductorExistente() throws ConductorNoEncontradoException {
        String nombreEsperado = "redirect:/perfil";
        Integer idUsuario = 1;
        Conductor nuevo= new Conductor();

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        ModelAndView mav=this.conductorControlador.editarConductor(session,nuevo);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        verify(conductorServicio).editarConductor(nuevo);
        verify(session).setAttribute("isEditForm", false);
    }

    @Test
    public void queNoSePuedaEditarUnConductorInexistente() throws ConductorNoEncontradoException {
        String nombreEsperado = "registro-conductor";
        Integer idUsuario = 1;
        Conductor nuevo= new Conductor();
        nuevo.setId(idUsuario);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        doThrow(new ConductorNoEncontradoException("Conductor No Encontrado.")).when(conductorServicio).editarConductor(nuevo);

        ModelAndView mav=this.conductorControlador.editarConductor(session,nuevo);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), equalTo("Conductor No Encontrado."));
        verify(session, never()).setAttribute(eq("isEditForm"), anyBoolean());

    }
    @Test
    public void queUnConductorSubaUnaFotoDesdeElFormularioParaIngresarFotoDePerfil() throws ConductorNoEncontradoException, IOException {
        String nombreEsperado = "redirect:/perfil";
        Integer idUsuario = 1;
        MultipartFile imagen = mock(MultipartFile.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        ModelAndView mav = this.conductorControlador.subirFoto(imagen,session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        verify(conductorServicio).ingresarImagen(imagen,idUsuario);
    }

    @Test
    public void queUnConductorInexistenteNoSubaUnaFotoDesdeElFormularioParaIngresarFotoDePerfil() throws ConductorNoEncontradoException, IOException {
        String nombreEsperado = "registro-conductor";
        Integer idUsuario = 1;
        MultipartFile imagen = mock(MultipartFile.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doThrow(new ConductorNoEncontradoException("Conductor no encontrado")).when(conductorServicio).ingresarImagen(imagen, idUsuario);

        ModelAndView mav = this.conductorControlador.subirFoto(imagen,session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), equalTo("Conductor no encontrado"));
        verify(conductorServicio).ingresarImagen(imagen,idUsuario);
    }

    @Test
    public void queUnConductorExistenteNoSubaUnaFotoDesdeElFormularioParaIngresarFotoDePerfilPorIOException() throws ConductorNoEncontradoException, IOException {
        String nombreEsperado = "registro-conductor";
        Integer idUsuario = 1;
        MultipartFile imagen = mock(MultipartFile.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doThrow(new IOException("Error al subir la imagen")).when(conductorServicio).ingresarImagen(imagen, idUsuario);

        ModelAndView mav = this.conductorControlador.subirFoto(imagen,session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), equalTo("Error al subir la imagen"));
        verify(conductorServicio).ingresarImagen(imagen,idUsuario);
    }

    @Test
    public void queSeBorreUnConductorExistente() throws ConductorNoEncontradoException {
        String nombreEsperado = "redirect:/cerrar-sesion";
        Integer idUsuario = 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        ModelAndView mav = this.conductorControlador.borrarCuenta(session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        verify(conductorServicio).borrarConductor(idUsuario);
    }

    @Test
    public void queSeNoSeBorreUnConductorInexistente() throws ConductorNoEncontradoException {
        String nombreEsperado = "registro-conductor";
        Integer idUsuario = 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doThrow(new ConductorNoEncontradoException("Conductor no encontrado")).when(conductorServicio).borrarConductor(idUsuario);

        ModelAndView mav = this.conductorControlador.borrarCuenta(session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), equalTo("Conductor no encontrado"));
        verify(conductorServicio).borrarConductor(idUsuario);
    }



}
